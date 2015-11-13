Description
===========
Sample application demonstrating how to implement a custom UserDetailsService with Spring Security 3.1 and Spring Data JPA

To read the whole guide, please see the original blog:

"Spring Security 3.1 - Implement UserDetailsService with Spring Data JPA"
http://krams915.blogspot.com/2012/01/spring-security-31-implement_5023.html
********
This project here is based on original project at: https://github.com/krams915/spring-security-tutorial

It differs from the original project in following aspects:
- Database model changed a bit
- Added a new role ROLE_HR
- Users that can be used to login to the applications: user/1234, hr/1234 and admin/1234

********
### TODO - Add ManyToMany relationship between users and roles.
********

Building
========

Please visit http://krams915.blogspot.com/2012/01/spring-security-31-implement.html for full instructions

Notes
=====
For more tutorials, please visit http://krams915.blogspot.com/
