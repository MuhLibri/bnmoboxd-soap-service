CREATE TABLE logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    endpoint VARCHAR(255) NOT NULL,
    client_ip VARCHAR(255) NOT NULL,
    request_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    soap_request TEXT
);

CREATE TABLE subscriptions (
    curator_username VARCHAR(255) NOT NULL,
    subscriber_username VARCHAR(255) NOT NULL,
    status enum('PENDING', 'ACCEPTED', 'REJECTED') NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (curator_username, subscriber_username)
);

INSERT INTO logs (description, endpoint, client_ip, soap_request)
VALUES
    ('Log 1', '/api/endpoint1', '192.168.1.1', '<SOAP request 1>'),
    ('Log 2', '/api/endpoint2', '192.168.1.2', '<SOAP request 2>'),
    ('Log 3', '/api/endpoint3', '192.168.1.3', '<SOAP request 3>');

INSERT INTO subscriptions (curator_username, subscriber_username, status)
VALUES
    ('curator1', 'user1', 'ACCEPTED'),
    ('curator1', 'user2', 'PENDING'),
    ('curator1', 'user3', 'ACCEPTED'),
    ('curator1', 'user4', 'PENDING'),
    ('curator1', 'user4', 'ACCEPTED'),
    ('curator1', 'user6', 'REJECTED'),
    ('curator2', 'user1', 'PENDING'),
    ('curator2', 'user4', 'REJECTED'),
    ('curator2', 'user5', 'PENDING'),
    ('curator2', 'user6', 'ACCEPTED'),
    ('curator2', 'user7', 'PENDING'),
    ('curator2', 'user8', 'REJECTED'),
    ('curator3', 'user5', 'PENDING'),
    ('curator3', 'user6', 'ACCEPTED'),
    ('curator4', 'user1', 'PENDING'),
    ('curator4', 'user2', 'PENDING'),
    ('curator4', 'user7', 'ACCEPTED'),
    ('curator4', 'user8', 'PENDING'),
    ('curator5', 'user3', 'PENDING'),
    ('curator5', 'user6', 'REJECTED'),
    ('curator5', 'user7', 'PENDING'),
    ('curator5', 'user8', 'ACCEPTED'),
    ('curator5', 'user9', 'PENDING'),
    ('curator6', 'user3', 'PENDING'),
    ('curator6', 'user5', 'PENDING'),
    ('curator6', 'user6', 'REJECTED'),
    ('curator6', 'user8', 'PENDING'),
    ('curator7', 'user1', 'ACCEPTED'),
    ('curator7', 'user2', 'PENDING'),
    ('curator7', 'user3', 'PENDING'),
    ('curator7', 'user4', 'REJECTED'),
    ('curator8', 'user5', 'PENDING'),
    ('curator8', 'user6', 'PENDING'),
    ('curator8', 'user7', 'PENDING'),
    ('curator9', 'user8', 'PENDING'),
    ('curator9', 'user9', 'REJECTED'),
    ('curator9', 'user10', 'PENDING'),
    ('curator10', 'user1', 'REJECTED'),
    ('curator10', 'user4', 'REJECTED'),
    ('curator10', 'user6', 'REJECTED');