
drop table if exists public.tb_usuario_permissao_client;
create table public.tb_usuario_permissao_client(
 id serial primary key
 , login varchar(50)
 , client_id varchar(255)
 , permissao varchar(100)
);
insert into public.tb_usuario_permissao_client( login , client_id , permissao) values( 'efraim' , 'cliente1' , 'USUARIO_VER_TOKENS' );
insert into public.tb_usuario_permissao_client( login , client_id , permissao) values( 'efraim' , 'cliente1' , 'USUARIO_LISTAR' );
insert into public.tb_usuario_permissao_client( login , client_id , permissao) values( 'efraim2' , 'cliente1' , 'USUARIO_VER_TOKENS' );

insert into public.tb_usuario( login , password , nome) values ( 'efraim2' , md5('password') , 'Efraim 2');


insert into public.oauth_client_details(
 client_id,resource_ids,client_secret,scope,authorized_grant_types,autoapprove
) values
 ( 'cliente1' , 'resource1,resource2' , 'appsecret' , 'read', 'implicit', 'true')
,( 'cliente2' , 'resource1' , 'appsecret' , 'read', 'implicit', 'true');
