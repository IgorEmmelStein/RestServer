-- 1. Cria a base de dados que o JPA espera (conforme persistence.xml)
CREATE DATABASE IF NOT EXISTS `comida` 
DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 2. Seleciona a nova base de dados
USE `comida`;

-- 3. Cria a tabela 'comida' (estrutura do ExemploSQL.sql)
DROP TABLE IF EXISTS `comida`;

CREATE TABLE `comida` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `preco` DECIMAL(10, 2) NOT NULL, 
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_general_ci;

-- 4. Insere dados iniciais (opcional, pra teste)
INSERT INTO `comida` (`nome`, `preco`) VALUES
('Pizza Calabresa', 39.90),
('Hambúrguer Clássico', 22.50),
('Refrigerante Lata', 6.50);

select nome from comida;

ALTER TABLE `comida`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=100;

COMMIT;