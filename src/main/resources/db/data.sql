truncate table users cascade;
truncate table media cascade;
truncate table playlists cascade;
truncate table playlist_media cascade;

insert into users(id, email, password, time_created) values
(200, 'john@email.com', '$2a$10$6rIpDTj3/hiYiHdnzooaWuSjGTZT8C88aIuRlo9Lph./ZY71fsl5S', '2024-06-04T15:03:03.792009700'),
(201, 'jane@email.com', 'password', '2024-06-04T15:03:03.792009700'),
(202, 'johnny@email.com', 'password', '2024-06-04T15:03:03.792009700'),
(203, 'james@email.com', 'password', '2024-06-04T15:03:03.792009700');

insert into playlists(id, name, description, time_created, uploader_id) values
(300, 'name 1', 'description', '2024-06-04T15:03:28.739603300', 201),
(301, 'name 2', 'description1', '2024-06-04T15:03:28.739603300', 200),
(302, 'name 3', 'description2', '2024-06-04T15:03:28.739603300', 202);

insert into media (id, category, description, url, time_created, uploader_id) values
(100, 'ACTION', 'media 1', 'https://www.cloudinary.com/media1', '2024-06-04T15:03:03.792009700', 200),
(101, 'ACTION', 'media 2', 'https://www.cloudinary.com/media2', '2024-06-04T15:03:03.792009700', 200),
(102, 'STEP_MOM', 'media 3', 'https://www.cloudinary.com/media3', '2024-06-04T15:03:03.792009700', 201),
(103, 'COMEDY', 'media 4', 'https://www.cloudinary.com/media4', '2024-06-04T15:03:03.792009700', 200),
(104, 'ROMANCE', 'media 5', 'https://www.cloudinary.com/media5', '2024-06-04T15:03:03.792009700', 201);

insert into playlist_media(playlist_id, media_id) VALUES
(300, 100),
(300, 101),
(300, 103),
(301, 102),
(301, 104);


