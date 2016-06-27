from flask import Flask
from flask_jwt import JWT, jwt_required, current_identity, JWTError
from functools import wraps
import jwt

app  = Flask(__name__)

app.config['RESOURCE_ID'] = 'resource2'
app.config['SECRET_KEY'] = '123456'
app.config['JWT_REQUIRED_CLAIMS'] = [ 'jti' ]
app.config['JWT_VERIFY_CLAIMS'] = [ 'jti']
app.config['JWT_AUTH_HEADER_PREFIX'] = 'Bearer'
app.config['JWT_ALGORITHM'] = 'HS256'


def identity(payload):
  print payload
  return payload


def authenticate(username, password):
    return None


def _jwt_decode_callback(token):
    secret = app.config['JWT_SECRET_KEY']
    algorithm = app.config['JWT_ALGORITHM']
    leeway = app.config['JWT_LEEWAY']

    verify_claims = app.config['JWT_VERIFY_CLAIMS']
    required_claims = app.config['JWT_REQUIRED_CLAIMS']

    options = {
        'verify_' + claim: True
        for claim in verify_claims
    }

    options.update({
        'require_' + claim: True
        for claim in required_claims
    })

    return jwt.decode(token, secret, options=options, algorithms=[algorithm], leeway=leeway , audience =  app.config['RESOURCE_ID'] )

_jwt = JWT(app , authenticate , identity )
_jwt.jwt_decode_callback = _jwt_decode_callback


def has_client_authoritie(authority):
    """View decorator that requires a valid JWT token to be present in the request

    :param realm: an optional realm
    """
    def wrapper(fn):
        @wraps(fn)
        def decorator(*args, **kwargs):
            authorities = current_identity['user_client_authorities']
            print( authority )
            if authority in authorities:
              print( authorities )
            else:
              raise JWTError('Invalid user authority', "User doesn't have the required authority" )            
          
            return fn(*args, **kwargs)
        return decorator
    return wrapper



#@app.route("/")
#def hello():
    #return "Hello World!"

@app.route('/protected')
@jwt_required()
@has_client_authoritie("PERM_USUARIO_LISTAR")
def return_current_time():
    return '%s' % current_identity    



if __name__ == "__main__":
    app.run()