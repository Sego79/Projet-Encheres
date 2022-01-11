use bdd_encheres
go



insert into CATEGORIES values 
('toutes', 'Toutes'),
('meuble', 'Ameublement'), 
('infor', 'Informatique'),
('sport_loisir', 'Sports/Loisir'),
('vetements', 'Vêtements'),
('divers', 'Divers')

SELECT * FROM CATEGORIES;

INSERT INTO UTILISATEURS VALUES ('toto91', 'dupont', 'thomas', 'thomas@dupont.com', '01.01.01.01.01', 'rue1', '35353', 'Laville', 'totototo', 500, 1, 1);
INSERT INTO UTILISATEURS VALUES ('Dede85', 'dubois', 'didier', 'didier@dubois.com', '02.02.02.02.02', 'rue1','36363', 'Villedeux', 'totototo', 600, 0, 1);
INSERT INTO UTILISATEURS VALUES ('Gerard45', 'dubois', 'gerard', 'gerard@dubois.com', '02.02.02.02.02', 'rue1','36363', 'Villedeux', 'totototo', 600, 0, 1);


SELECT * FROM UTILISATEURS;

INSERT INTO ARTICLES_VENDUS VALUES ('Fauteuil', 'un fauteuil rouge très confortable', '/images/logoProjet_Taille_reduite.png', '2021-12-06', '16:16:16', '2021-12-20','16:16:16', 125, null, 1, 2, 0, 0);
INSERT INTO ARTICLES_VENDUS VALUES ('Ecran ordinateur Apple', 'Ecran Apple en très bon état, juste une petite rayure sur le bord', '/images/fauteuil.jpg', '2021-12-06','17:17:17', '2021-12-08','17:17:17', 300, 420, 2, 3, 0, 0);
INSERT INTO ARTICLES_VENDUS VALUES ('Panier de basket', 'Panier de basket abimé mais avec matériel de fixation en bon état', '/images/logoProjet_Taille_reduite.png', '2021-12-06', '16:16:16', '2021-12-30','16:16:16', 50, null, 1, 4, 0, 0);
INSERT INTO ARTICLES_VENDUS VALUES ('Pantalon', 'Pantalon comme neuf porté une seule fois', '/images/fauteuil.jpg', '2021-12-06', '16:16:16', '2021-12-20','16:16:16', 115, null, 2, 5, 0, 0);
INSERT INTO ARTICLES_VENDUS VALUES ('Orchidée', 'Orchidée déjà en fleurs', '/images/logoProjet_Taille_reduite.png', '2021-12-06', '16:16:16', '2021-12-20','16:16:16', 25, null, 2, 6, 0, 0);
INSERT INTO ARTICLES_VENDUS VALUES ('Rose', 'Orchidée déjà en fleurs', '/images/fauteuil.jpg', '2021-12-20', '16:16:16', '2021-12-25','16:16:16', 25, null, 1, 6, 0, 0);

INSERT INTO RETRAITS VALUES (1, 'rue1', '35353', 'Laville');
INSERT INTO RETRAITS VALUES (2, 'rue1', '35353', 'Laville');
INSERT INTO RETRAITS VALUES (3, 'rue1', '35353', 'Laville');
INSERT INTO RETRAITS VALUES (4, 'rue1', '35353', 'Laville');
INSERT INTO RETRAITS VALUES (5, 'rue1', '35353', 'Laville');
INSERT INTO RETRAITS VALUES (6, 'rue1', '35353', 'Laville');

SELECT * FROM ARTICLES_VENDUS;
SELECT * FROM RETRAITS;

INSERT INTO [ENCHERES] VALUES (1, 1, '2021-12-15','10:10:10', 127);

SELECT * FROM ENCHERES;
