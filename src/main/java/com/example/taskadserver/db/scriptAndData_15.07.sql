-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.5.18-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping data for table taskadserver.tasks: ~9 rows (approximately)
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
INSERT INTO `tasks` (`task_id`, `description`, `user_id`, `status`, `due_date`, `title`)
VALUES (1, 'Developing Java applications and implementing software solutions ', 2, 'PENDING', '2023-07-27',
        'Writing code for Project 1'),
       (2, 'Identifying and fixing errors, bugs, and performance issues', 2, 'PENDING', '2023-07-21',
        'Debugging and troubleshooting Component 2 of Project 1'),
       (3,
        'Participating in code reviews to provide feedback and ensure adherence to coding standards, best practices, and design principles.',
        2, 'DISCARDED', '2023-07-15', 'Code review of PR 1, 3, 6'),
       (4,
        'Adding new features and functionality to existing microservice, based on user requirements or system improvements',
        2, 'COMPLETED', '2023-07-15', 'Enhancing existing functionality of Micriservice Payments'),
       (5,
        'Designing and executing unit tests, integration tests, and system tests to verify the correctness and reliability of the code.',
        2, 'COMPLETED', '2023-07-13', 'Testing and quality assurance of the Component 1'),
       (6, 'Analyzing and optimizing Java applications for better performance, scalability, and efficiency.', 1,
        'PENDING', '2023-07-29', 'Performance optimization of Component 4'),
       (7,
        'Creating technical documentation, such as code documentation, API documentation, and user guides, to facilitate maintenance and usage of the software.',
        1, 'COMPLETED', '2023-07-13', 'Writing the documentation for component 3 '),
       (8, 'Keeping up-to-date with the latest Java 21 framework', 1, 'PENDING', '2023-07-31',
        'Research and learning on the new Java 21 release'),
       (9,
        'Using bug tracking systems to track reported issues, investigating and resolving them till the end of the week.',
        1, 'PENDING', '2023-07-20', 'Bug tracking and issue resolution for Bug #15');
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;

-- Dumping data for table taskadserver.users: ~2 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`user_id`, `first_name`, `last_name`, `password`, `username`)
VALUES (1, 'Georgi', 'Georgiev', 'pass1', 'gosho99'),
       (2, 'Petar', 'Petrov', 'pass', 'pesho99');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
