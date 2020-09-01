-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        8.0.21 - MySQL Community Server - GPL
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 테이블 search_engine.database_list 구조 내보내기
CREATE TABLE IF NOT EXISTS `database_list` (
  `idx` int NOT NULL AUTO_INCREMENT COMMENT 'idx',
  `db_conn_ip` varchar(50) DEFAULT NULL,
  `db_id` varchar(50) DEFAULT NULL,
  `db_pw` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='데이터베이스 관리 테이블';

-- 테이블 데이터 search_engine.database_list:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `database_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `database_list` ENABLE KEYS */;

-- 테이블 search_engine.service 구조 내보내기
CREATE TABLE IF NOT EXISTS `service` (
  `idx` int DEFAULT NULL,
  `service_id` varchar(50) DEFAULT NULL,
  `service_detail` text,
  `bulk_query` text,
  UNIQUE KEY `idx` (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='선택된 DB 관리 테이블의 service 정보';

-- 테이블 데이터 search_engine.service:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
/*!40000 ALTER TABLE `service` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
