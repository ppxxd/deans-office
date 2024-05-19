INSERT INTO positions (NAME, RESPONSIBILITIES) //ALSO HAVE ENUMS FOR THAT IN ru/ppxxd/deansoffice/model/enums/PositionsNames.java
VALUES ('Преподаватель', 'Проведение занятий'),
       ('Старший преподаватель', 'Проведение занятий и лекций'),
       ('Доцент', 'Проведение занятий, лекций, подготовка учебных материалов по программе'),
       ('Профессор', 'Проведение занятий, лекций, подготовка учебных материалов по программе, ' ||
                        'составление учебной программы');

INSERT INTO chairs (name, head_last_name, head_first_name, head_patronymic, chair_phone_number) --TODO ВСТАВИТЬ ЕЩЕ ПРИМЕРОВ
VALUES ('Управления и информатики в технических системах', 'Чеканин', 'Владислав', 'Александрович', 79101234567);

--TODO вставить еще groups, subjects, 1 teacher 1 student FOR SCHEDULE