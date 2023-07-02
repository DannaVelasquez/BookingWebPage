
------------------USERS----------------------
--    JWT and Roles    --
INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER') ON DUPLICATE KEY UPDATE   name='ROLE_USER';
INSERT INTO roles (id, name) VALUES (2, 'ROLE_ADMIN') ON DUPLICATE KEY UPDATE  name='ROLE_ADMIN';


------------------POLITICS----------------------
INSERT INTO politics (id, house_rules, health_and_safety, cancellation_policies)
VALUES (1, 'Por respeto a los demás huéspedes, se solicita mantener el ruido al mínimo después de las 10 p.m.',
  'Los participantes deben informar cualquier condición médica preexistente antes del viaje.',
  'Si se realiza una cancelación debido a circunstancias imprevistas, se emitirá un crédito para futuras reservas.')
ON DUPLICATE KEY UPDATE house_rules = VALUES(house_rules), health_and_safety = VALUES(health_and_safety), cancellation_policies = VALUES(cancellation_policies);

INSERT INTO politics (id, house_rules, health_and_safety, cancellation_policies)
VALUES (2, 'No se permiten mascotas dentro de la casa.',
  'Se recomienda encarecidamente tener un seguro de viaje que cubra cualquier emergencia médica.',
  'Las cancelaciones realizadas dentro de los 7 días antes del viaje no son reembolsables.')
ON DUPLICATE KEY UPDATE house_rules = VALUES(house_rules), health_and_safety = VALUES(health_and_safety), cancellation_policies = VALUES(cancellation_policies);

INSERT INTO politics (id, house_rules, health_and_safety, cancellation_policies)
VALUES (3, 'Queda estrictamente prohibido el acceso a áreas restringidas sin autorización previa.',
  'La edad mínima para participar en el viaje es de 18 años, a menos que estén acompañados por un adulto.',
  'Se permite cancelar la reserva sin cargo adicional hasta 30 días antes del viaje.')
ON DUPLICATE KEY UPDATE house_rules = VALUES(house_rules), health_and_safety = VALUES(health_and_safety), cancellation_policies = VALUES(cancellation_policies);

INSERT INTO politics (id, house_rules, health_and_safety, cancellation_policies)
VALUES (4, 'No se permiten mascotas dentro de la casa.',
  'Por razones de seguridad, se deben seguir las instrucciones del personal en caso de una evacuación de emergencia.',
  'Las cancelaciones realizadas entre 15 y 29 días antes del viaje estarán sujetas a una tarifa de cancelación del 50%.')
ON DUPLICATE KEY UPDATE house_rules = VALUES(house_rules), health_and_safety = VALUES(health_and_safety), cancellation_policies = VALUES(cancellation_policies);

INSERT INTO politics (id, house_rules, health_and_safety, cancellation_policies)
VALUES (5, 'No se permiten mascotas dentro de la casa.',
  'El agua del grifo es potable y segura para beber.',
  'Las cancelaciones realizadas entre 15 y 29 días antes del viaje estarán sujetas a una tarifa de cancelación del 50%.')
ON DUPLICATE KEY UPDATE house_rules = VALUES(house_rules), health_and_safety = VALUES(health_and_safety), cancellation_policies = VALUES(cancellation_policies);

