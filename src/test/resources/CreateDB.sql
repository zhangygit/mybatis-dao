/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.6.21-log : Database - mapper_test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mapper_test` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mapper_test`;

/*Table structure for table `user` */

CREATE TABLE `user` (
  `user_id` varchar(32) NOT NULL,
  `user_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`user_id`,`user_name`) values ('07e2eb092c50459594156054368106f0','list1'),('200cb94c99e64b21aed5e10b94f09eeb','newHotl'),('42e4e3ca6d874d8993f6ea190bbcaf66','list2'),('4c4680de3b274f8d860afb4f704ac624','huttol'),('5415f827a8e84400b31120dc7b17ff75','list2'),('760b6195d7284fc78728d1df35b2f70e','list1'),('9fab0adc74e14621a3d4e97e05eba600','huttol');

/*Table structure for table `userinfo` */

CREATE TABLE `userinfo` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `e_mail` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

/*Data for the table `userinfo` */

insert  into `userinfo`(`id`,`user_id`,`phone`,`e_mail`) values (34,'200cb94c99e64b21aed5e10b94f09eeb',NULL,'wangli@163.com'),(35,'200cb94c99e64b21aed5e10b94f09eeb',NULL,'wangli@163.com'),(36,'200cb94c99e64b21aed5e10b94f09eeb',NULL,'wangli@163.com'),(37,'200cb94c99e64b21aed5e10b94f09eeb','5454','wangli@163.com'),(38,'200cb94c99e64b21aed5e10b94f09eeb','5454','wangli@163.com'),(39,'42e4e3ca6d874d8993f6ea190bbcaf66','1869658556','list1'),(40,'42e4e3ca6d874d8993f6ea190bbcaf66','1869658556','list1'),(41,'42e4e3ca6d874d8993f6ea190bbcaf66','1869658556','list1'),(42,'42e4e3ca6d874d8993f6ea190bbcaf66','1869658556','list1'),(43,'200cb94c99e64b21aed5e10b94f09eeb','5454','wangli@163.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
