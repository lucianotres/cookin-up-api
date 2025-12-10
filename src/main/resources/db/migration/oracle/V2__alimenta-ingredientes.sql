INSERT INTO categoria (id, nome, imagem) VALUES
(1, 'Laticínios e Ovos', 'laticinios_e_ovos.png'),
(2, 'Farinhas e Fermentos', 'farinhas_e_fermentos.png'),
(3, 'Temperos e Especiarias', 'temperos_e_especiarias.png'),
(4, 'Óleos, Gorduras e Vinagres', 'oleos_gorduras_e_vinagres.png'),
(5, 'Hortaliças e Verduras', 'hortalicas_e_verduras.png'),
(6, 'Açúcares e Adoçantes', 'acucares_e_adocantes.png'),
(7, 'Proteínas Animais', 'proteinas_animais.png'),
(8, 'Grãos, Cereais e Leguminosas', 'graos_cerais_e_leguminosas.png'),
(9, 'Frutas frescas', 'frutas_frescas.png'),
(10, 'Frutas secas', 'frutas_secas.png'),
(11, 'Pães e Massas', 'paes_e_massas.png'),
(12, 'Doces e guloseimas', 'doces_e_guloseimas.png');

INSERT INTO ingrediente (id, id_categoria, nome) VALUES
-- Categoria 1: Laticínios e Ovos
(1, 1, 'Ovos'),
(2, 1, 'Queijo'),
(3, 1, 'Leite'),
(4, 1, 'Manteiga'),
(5, 1, 'Creme de Leite'),
(6, 1, 'Iogurte'),
(7, 1, 'Leite Condensado'),
(8, 1, 'Sorvete'),

-- Categoria 2: Farinhas e Fermentos
(9, 2, 'Farinha de trigo'),
(10, 2, 'Polvilho'),
(11, 2, 'Farinha de rosca'),
(12, 2, 'Canjica'),
(13, 2, 'Farinha de mandioca'),
(14, 2, 'Fubá'),
(15, 2, 'Linhaça'),
(16, 2, 'Fermento químico'),

-- Categoria 3: Temperos e Especiarias
(17, 3, 'Canela'),
(18, 3, 'Cravo'),
(19, 3, 'Orégano'),
(20, 3, 'Noz moscada'),
(21, 3, 'Tomilho'),
(22, 3, 'Pimenta do Reino'),
(23, 3, 'Cominho'),

-- Categoria 4: Óleos, Gorduras e Vinagres
(24, 4, 'Vinagre'),
(25, 4, 'Óleo'),
(26, 4, 'Dendê'),
(27, 4, 'Azeite de Oliva'),
(28, 4, 'Banha'),
(29, 4, 'Aceto Balsâmico'),
(30, 4, 'Óleo de coco'),

-- Categoria 5: Hortaliças e Verduras
(31, 5, 'Cebola'),
(32, 5, 'Alho'),
(33, 5, 'Tomate'),
(34, 5, 'Abóbora'),
(35, 5, 'Abobrinha'),
(36, 5, 'Batata'),
(37, 5, 'Pimentão'),
(38, 5, 'Espinafre'),
(39, 5, 'Cenoura'),

-- Categoria 6: Açúcares e Adoçantes
(40, 6, 'Açúcar branco'),
(41, 6, 'Açúcar mascavo'),
(42, 6, 'Açúcar cristal'),
(43, 6, 'Melado'),
(44, 6, 'Mel'),
(45, 6, 'Baunilha'),
(46, 6, 'Glucose'),
(47, 6, 'Xilito'),
(48, 6, 'Stevia'),

-- Categoria 7: Proteínas Animais
(49, 7, 'Peixe'),
(50, 7, 'Carne bovina'),
(51, 7, 'Carne de porco'),
(52, 7, 'Frango'),
(53, 7, 'Bacon'),
(54, 7, 'Salsicha'),
(55, 7, 'Atum'),
(56, 7, 'Salmão'),
(57, 7, 'Presunto'),
(58, 7, 'Bacalhau'),

-- Categoria 8: Grãos, Cereais e Leguminosas
(59, 8, 'Arroz'),
(60, 8, 'Feijão'),
(61, 8, 'Aveia'),
(62, 8, 'Ervilha'),
(63, 8, 'Lentilha'),
(64, 8, 'Grão de bico'),
(65, 8, 'Milho'),
(66, 8, 'Gergelim'),
(67, 8, 'Quinoa'),

-- Categoria 9: Frutas frescas
(68, 9, 'Banana'),
(69, 9, 'Maçã'),
(70, 9, 'Uva'),
(71, 9, 'Pera'),
(72, 9, 'Limão'),
(73, 9, 'Morango'),
(74, 9, 'Ameixa'),
(75, 9, 'Framboesa'),
(76, 9, 'Acabaxi'),

-- Categoria 10: Frutas secas
(77, 10, 'Castanha de caju'),
(78, 10, 'Castanha do pará'),
(79, 10, 'Uva passa'),
(80, 10, 'Damasco'),
(81, 10, 'Tâmara'),
(82, 10, 'Pistache'),
(83, 10, 'Amêndoa'),
(84, 10, 'Amendoim'),

-- Categoria 11: Pães e Massas
(85, 11, 'Pão'),
(86, 11, 'Pão sírio'),
(87, 11, 'Tortilha'),
(88, 11, 'Macarrão'),
(89, 11, 'Nhoque'),
(90, 11, 'Massa de pastel'),
(91, 11, 'Massa de lasanha'),
(92, 11, 'Biscoito'),
(93, 11, 'Broa'),

-- Categoria 12: Doces e guloseimas
(94, 12, 'Chocolate'),
(95, 12, 'Geleia'),
(96, 12, 'Goiabada'),
(97, 12, 'Caramelo'),
(98, 12, 'Chantilly'),
(99, 12, 'Cacau em pó'),
(100, 12, 'Suspiro'),
(101, 12, 'Gelatina'),
(102, 12, 'Paçoca');