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
    ('user1', 'user2', 'ACCEPTED'),
    ('user2', 'user3', 'PENDING'),
    ('user3', 'user1', 'REJECTED');