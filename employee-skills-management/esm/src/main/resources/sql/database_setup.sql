CREATE TABLE `skill` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

CREATE TABLE `employee` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL DEFAULT '',
  `last_name` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

CREATE TABLE employee_skills (
   employee_id INT(11) UNSIGNED NOT NULL,
   employee_skill_id INT(11) UNSIGNED NOT NULL,
  CONSTRAINT FK_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employee (id),
  CONSTRAINT FK_SKILL FOREIGN KEY (employee_skill_id) REFERENCES skill (id),
  PRIMARY KEY (employee_id, employee_skill_id)
) ENGINE = InnoDB ROW_FORMAT = DEFAULT CHARACTER SET utf8;
