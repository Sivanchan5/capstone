
--Insert shelter data
INSERT INTO shelters(name, address, contact_number) VALUES
('Happy Paws Shelter', '123 Main St, Seattle', '123-456-7890'),
('Friendly Tails', '456 Elm St, Portland', '987-654-3210'),
('Kazu', '1 Butternut Trail', '120-660-3408'),
 ('Riffpath', '2350 2nd Street', '612-199-8036')
ON DUPLICATE KEY UPDATE
                     name = VALUES(name),
                     address = VALUES(address),
                     contact_number = VALUES(contact_number);


-- Insert dog data
INSERT IGNORE INTO dogs(name, breed, age, shelter_id, image_url) VALUES
('Buddy', 'boston_terrier', 3, 1, '/uploads/image1.jpg'),
('Bella', 'shih_tzu', 2, 2, '/uploads/image2.jpg'),
('Max', 'pembroke_welsh_corgi', 4, 1, '/uploads/image3.jpg'),
('Luna', 'shiba_inu', 1, 3, '/uploads/image4.jpg'),
('Rocky', 'boston_terrier', 5, 2, '/uploads/image5.jpg'),
('Shadow', 'german_spitz', 3, 4, '/uploads/image6.jpg'),
('Nova', 'siberian_husky', 4, 1, '/uploads/image7.jpg'),
('Daisy', 'american_pit_bull_terrier', 6, 3, '/uploads/image8.jpg');

--Insert sample user data
INSERT IGNORE INTO users(user_name, email, password,role) VALUES
('john_doe', 'john@gmail.com','password123','USER'),
('jane_smith','jane@gmail.com','securepass456','USER');


--Insert adoption record data
INSERT INTO adoptions(user_id, dog_id, status, adoption_date)VALUES
(1, 1, 'PENDING', '2024-01-10')
ON DUPLICATE KEY UPDATE
                     status = VALUES(status), adoption_date = VALUES(adoption_date);
INSERT INTO adoptions(user_id, dog_id, status, adoption_date)VALUES
(2,3, 'APPROVED', '2023-12-15')
ON DUPLICATE KEY UPDATE
                     status = VALUES(status), adoption_date = VALUES(adoption_date);