-- Configurações básicas
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE DATABASE IF NOT EXISTS `pessoa` 
DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- ==========================================================
-- 1. LIMPEZA E CRIAÇÃO DA TABELA `comida`
-- ==========================================================

-- Se a tabela 'pessoa' ainda existir e quiseres limpá-la
DROP TABLE IF EXISTS `pessoa`;

-- 
-- ESTRUTURA DA TABELA `comida`
--
CREATE TABLE `comida` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  -- DECIMAL(10, 2) é o tipo mais adequado para valores monetários/double
  `preco` DECIMAL(10, 2) NOT NULL, 
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ==========================================================
-- 2. INSERÇÃO DE DADOS INICIAIS (OPCIONAL, para teste)
-- ==========================================================

INSERT INTO `comida` (`nome`, `preco`) VALUES
('Pizza Calabresa', 39.90),
('Hambúrguer Clássico', 22.50),
('Refrigerante Lata', 6.50);

-- ==========================================================
-- 3. AJUSTES FINAIS
-- ==========================================================

-- O AUTO_INCREMENT deve ser modificado após a inserção (ou mantido pelo JPA)
ALTER TABLE `comida`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=100;
  
COMMIT;