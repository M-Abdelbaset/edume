insert into course (id, name, description, price, tutor, rating, category, version) values (1, 'learn Java in 10 day!', 'learn Java in 10 day!', 0.0, 'Mohamed Raheem', 1.5, 3, 0);
insert into course (id, name, description, price, tutor, rating, category, version) values (2, 'Spring Boot basics', 'Spring Boot basics', 9.9, 'Ali Khan', 3.0, 3, 0);
insert into course (id, name, description, price, tutor, rating, category, version) values (3, 'Introduction to Kubernates', 'Introduction to Kubernates', 9.9, 'Michael Spincer', 4.0, 3, 0);
insert into course (id, name, description, price, tutor, rating, category, version) values (4, 'Introduction to Docker', 'Introduction to Docker', 9.9, 'Michael Spincer', 4.3, 3, 0);
insert into course (id, name, description, price, tutor, rating, category, version) values (5, 'Junit', 'JUnit', 9.9, 'Michael Spincer', 4.0, 5, 0);
insert into course (id, name, description, price, tutor, rating, category, version) values (6, 'learn Python in 10 day!', 'learn Python in 10 day!', 0.0, 'Mohamed Raheem', 1.5, 5, 0);
insert into course (id, name, description, price, tutor, rating, category, version) values (7, 'Python for dummies!', 'Python for dummies!', 0.0, 'Mohamed Raheem', 1.5, 5, 0);
insert into course (id, name, description, price, tutor, rating, category, version) values (8, 'Introduction to Judo', 'Introduction to Judo', 9.9, 'Michael Spincer', 4.0, 8, 0);

-- related_courses table

-- related to learn Java in 10 day!
insert into related_courses (course, related_to, votes, version) values (1, 2, 20, 0);
insert into related_courses (course, related_to, votes, version) values (1, 3, 78, 0);
insert into related_courses (course, related_to, votes, version) values (1, 4, 2, 0);


-- related learn Python in 10 day!
insert into related_courses (course, related_to, votes, version) values (6, 7, 20, 0);