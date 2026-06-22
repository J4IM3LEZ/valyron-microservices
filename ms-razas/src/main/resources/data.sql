INSERT INTO razas (nombre, descripcion, bonus_fuerza, bonus_destreza, bonus_sabiduria, bonus_vitalidad, habilidad_innata, restricciones)
VALUES

    ('Elfico', 'Seres inmortales de gran sabiduria y destreza, guardianes de los bosques ancestrales', 2, 5, 5, 3, 'Vision del Bosque', NULL),

    ('Enano', 'Guerreros robustos de las montanas, maestros de la mineria y la forja', 5, 2, 3, 5, 'Furia de la Montana', 'No puede usar magia elfica'),

    ('Hobbit', 'Pequenos y agiles habitantes de la Comarca, expertos en sigilo', 1, 6, 4, 4, 'Sigilo de la Comarca', NULL),

    ('Hombre', 'Raza versatil y adaptable, capaz de dominar cualquier habilidad', 4, 4, 4, 4, 'Adaptabilidad', NULL),

    ('Orco', 'Guerreros salvajes de Mordor, temidos por su brutalidad', 6, 3, 1, 6, 'Furia Berserker', NULL),

    ('Mago Istari', 'Seres de poder ancestral enviados a guiar a las razas libres', 2, 3, 8, 2, 'Magia Ancestral', NULL);