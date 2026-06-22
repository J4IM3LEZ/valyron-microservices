INSERT INTO batallas (personaje_id, nombre_personaje, nombre_enemigo, tipo_enemigo, danio_infligido, danio_recibido, resultado, nivel_personaje, poder_usado, region_id, fecha_batalla, xp_ganada) VALUES
(1, 'Legolas', 'Lurtz', 'ORCO', 85, 20, 'VICTORIA', 1, 'Vision del Bosque', 1, NOW(), 150),
(2, 'Gimli', 'Goblin de Moria', 'GOBLIN', 60, 30, 'VICTORIA', 1, 'Furia de la Montana', 5, NOW(), 100),
(3, 'Frodo', 'Arane', 'ARANA', 20, 50, 'DERROTA', 1, 'Sigilo de la Comarca', 5, NOW(), 30),
(4, 'Aragorn', 'Capitan Nazgul', 'NAZGUL', 70, 60, 'EMPATE', 1, 'Adaptabilidad', 4, NOW(), 80),
(5, 'Lurtz', 'Soldado de Gondor', 'ESQUELETO', 90, 10, 'VICTORIA', 1, 'Furia Berserker', 4, NOW(), 120),
(6, 'Gandalf', 'Balrog', 'JEFE', 40, 200, 'DERROTA', 1, 'Magia Ancestral', 5, NOW(), 50);