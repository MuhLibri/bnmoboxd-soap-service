CREATE TABLE logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    endpoint VARCHAR(255) NOT NULL,
    client_ip VARCHAR(255) NOT NULL,
    request_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    soap_request TEXT
);

CREATE TABLE subscriptions (
    curator_id INT NOT NULL,
    subscriber_id INT NOT NULL,
    status enum('PENDING', 'ACCEPTED', 'REJECTED') NOT NULL DEFAULT 'PENDING',
    PRIMARY KEY (curator_id, subscriber_id)
);

INSERT INTO logs (description, endpoint, client_ip, soap_request)
VALUES
    ('Log 1', '/api/endpoint1', '192.168.1.1', '<SOAP request 1>'),
    ('Log 2', '/api/endpoint2', '192.168.1.2', '<SOAP request 2>'),
    ('Log 3', '/api/endpoint3', '192.168.1.3', '<SOAP request 3>');

INSERT INTO subscriptions (curator_id, subscriber_id, status)
VALUES
    (1, 2, 'ACCEPTED'),
    (2, 3, 'PENDING'),
    (3, 1, 'REJECTED');