SELECT * FROM db_telemarketing.tb_curso;
SELECT * FROM db_telemarketing.tb_ligacao;
SELECT * FROM db_telemarketing.tb_ligacao_oferece_curso;
SELECT * FROM db_telemarketing.tb_pessoa;

INSERT INTO `tb_curso` VALUES (1,'Curso Java e JSTL','Tags para facilitar o desenvolvimento JSP',1),(2,'Curso Servlet Parte 1','Fundamentos da programação web Java',1),(3,'Curso Servlet parte 2','Autenticação, autorização e o padrão MVC',1),(4,'Curso MySQL I','Iniciando suas consultas',1),(5,'Curso Angular parte 1','Fundamentos',1),(6,'Curso HTML5 e CSS3 I','Suas primeiras páginas da Web',1);

Insert into tb_pessoa values (1, 'Contato1', '999.999.999-91', 'email@gmail.com1', 'Pai1', 'Mae1',
 '99999-991', 'Endereco1', 'Bairro1', '(21)99999-9994', '(21)3276-8888', '(21)99999-9996', '(21)99999-9995',1),
 
(2, 'Contato2', '999.999.999-92', 'email@gmail.com2', 'Pai2', 'Mae2',
 '99999-992', 'Endereco2', 'Bairro2', '(22)99999-9994', '(22)3276-8888', '(22)99999-9996', '(22)99999-9995',1),
 
 (3, 'Contato3', '999.999.999-93', 'email@gmail.com3', 'Pai3', 'Mae3',
 '99999-993', 'Endereco3', 'Bairro3', '(33)99999-9994', '(33)3376-8888', '(33)99999-9996', '(33)99999-9995',1),
 
 (4, 'Contato4', '999.999.999-94', 'email@gmail.com4', 'Pai4', 'Mae4',
 '99999-994', 'Endereco4', 'Bairro4', '(44)99999-9994', '(44)4476-8888', '(44)99999-9996', '(44)99999-9995',1),
 
 (5, 'Contato5', '999.999.999-95', 'email@gmail.com5', 'Pai5', 'Mae5',
 '99999-995', 'Endereco5', 'Bairro5', '(55)99999-9995', '(55)5576-8888', '(55)99999-9996', '(55)99999-9995',1),
 
 (6, 'Contato6', '999.999.999-96', 'email@gmail.com6', 'Pai6', 'Mae6',
 '99999-996', 'Endereco6', 'Bairro6', '(66)99999-9996', '(66)6676-8888', '(66)99999-9996', '(66)99999-9996',1);

INSERT INTO tb_ligacao values (1, '10:00:00', '11:00:00', '2018-11-01', 'ofertado  1 curso',1,1),(2, '11:00:00', '12:00:00', '2018-11-02', 'ofertado  1 curso',2,1),(3, '13:00:00', '14:00:00', '2018-11-01', 'ofertado  1 curso',3,1), (4, '14:00:00', '15:00:00', '2018-11-05', 'ofertado   curso',4,1), (5, '15:00:00', '16:00:00', '2018-11-06', 'ofertado   curso',5,1), (6, '15:00:00', '16:00:00', '2018-11-06', 'ofertado   curso',6,1);

INSERT INTO tb_ligacao values (7,'11:00:00', '12:00:00', '2018-11-02', 'ofertado  1 curso',1,1);

INSERT INTO tb_ligacao_oferece_curso VALUES (1, 1, 1,0,0),(2, 2, 0,1,0),(3, 3, 0,0,1),(4,4, 1,1,1),(5,5,1,1,0),(6,6, 0,1,1);

INSERT INTO tb_ligacao_oferece_curso VALUES (7, 1, 1,0,0);

Select * from tb_ligacao where pessoa_id = 1;

Select l.*, p.nome,p.cpf, c.nome, c.descricao, c.id, loc.manha, loc.tarde, loc.noite 
from tb_ligacao as l, tb_pessoa as p, tb_curso as c, tb_ligacao_oferece_curso as loc 
Where 
l.pessoa_id = p.id and 
loc.ligacao_id = l.id and
loc.curso_id = c.id 
and l.pessoa_id = 1
and l.ativo =1;


SELECT L.ID, L.DATA_LIGACAO, P.NOME, C.NOME, LOC.*
FROM TB_LIGACAO L , TB_PESSOA P, TB_CURSO C, TB_LIGACAO_OFERECE_CURSO LOC
WHERE L.PESSOA_ID = P.ID
AND LOC.LIGACAO_ID = L.ID
AND LOC.CURSO_ID = C.ID
AND L.ATIVO =1
AND C.ATIVO =1
AND P.ATIVO 
ORDER BY L.DATA_LIGACAO;

	SELECT L.*, P.*, LOC.*, C.* 
	FROM TB_LIGACAO L, TB_PESSOA P ,TB_LIGACAO_OFERECE_CURSO LOC, TB_CURSO C
	WHERE L.PESSOA_ID = P.ID
	AND L.ID = LOC.LIGACAO_ID
	AND LOC.CURSO_ID = C.ID
	AND L.ATIVO = 1
	AND C.ATIVO = 1    
	;
 
 SELECT L.*, P.*, LOC.*, C.*
 FROM TB_LIGACAO L, TB_PESSOA P, TB_LIGACAO_OFERECE_CURSO LOC, TB_CURSO C
 WHERE L.PESSOA_ID = P.ID
 AND LOC.LIGACAO_ID = L.ID
 AND LOC.CURSO_ID = C.ID
 AND L.ID = 8
 AND P.ID = 1
 AND L.ATIVO = 1
 ;
 
 SELECT L.ID, L.DATA_LIGACAO, L.INICIO, L.FIM, L.OBSERVACOES, P.ID, P.NOME, P.SOBRE_NOME, P.CPF, P.CELULAR, P.RESIDENCIAL
				  FROM TB_LIGACAO L, TB_PESSOA P
				  WHERE L.PESSOA_ID = P.ID
				  AND L.ATIVO = 1
				 ORDER BY L.DATA_LIGACAO;
                 
SELECT L.*, LOC.*, C.*, P.*
FROM TB_LIGACAO L, TB_LIGACAO_OFERECE_CURSO LOC, TB_CURSO C, TB_PESSOA P 
WHERE LOC.LIGACAO_ID = L.ID
AND LOC.CURSO_ID = C.ID
AND P.ID = L.PESSOA_ID
AND L.ID = 21;

SELECT L.ID AS id_ligacao, L.DATA_LIGACAO, L.INICIO, L.FIM, L.OBSERVACOES,
				P.ID AS id_pessoa, P.NOME, P.SOBRE_NOME, P.CPF, P.CELULAR, P.RESIDENCIAL
				FROM TB_LIGACAO L, TB_PESSOA P
				WHERE L.PESSOA_ID = P.id_pessoa
				AND L.ATIVO = 1
				ORDER BY L.DATA_LIGACAO;	
 
 