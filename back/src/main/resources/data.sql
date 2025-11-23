INSERT INTO topic (title, description)
VALUES ('Topic #1', 'First topic'),
       ('Topic #2', 'Second topic'),
       ('Topic #3', 'Third topic');

INSERT INTO post (topic_id, title, content, created_at, created_by)
VALUES (1, 'Post #1', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, ante quis tincidunt tincidunt, libero massa tincidunt sapien, vitae molestie justo nibh quis nisi.', '2025-01-22', 'SYSTEM'),
       (1, 'Post #2', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, ante quis tincidunt tincidunt, libero massa tincidunt sapien, vitae molestie justo nibh quis nisi.', '2025-04-22', 'SYSTEM'),
       (2, 'Post #3', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, ante quis tincidunt tincidunt, libero massa tincidunt sapien, vitae molestie justo nibh quis nisi.', '2025-02-22', 'SYSTEM'),
       (2, 'Post #4', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, ante quis tincidunt tincidunt, libero massa tincidunt sapien, vitae molestie justo nibh quis nisi.', '2025-09-22', 'SYSTEM'),
       (3, 'Post #5', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, ante quis tincidunt tincidunt, libero massa tincidunt sapien, vitae molestie justo nibh quis nisi.', '2025-07-22', 'SYSTEM'),
       (3, 'Post #6', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, ante quis tincidunt tincidunt, libero massa tincidunt sapien, vitae molestie justo nibh quis nisi.', '2025-03-22', 'SYSTEM');

INSERT INTO comment (post_id, content, created_at, created_by)
VALUES (1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, ante quis tincidunt tincidunt, libero massa tincidunt sapien, vitae molestie justo nibh quis nisi.', '2025-01-22', 'SYSTEM'),
       (1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, ante quis tincidunt tincidunt, libero massa tincidunt sapien, vitae molestie justo nibh quis nisi.', '2025-04-22', 'SYSTEM'),
       (2, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, ante quis tincidunt tincidunt, libero massa tincidunt sapien, vitae molestie justo nibh quis nisi.', '2025-02-22', 'SYSTEM'),
       (2, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, ante quis tincidunt tincidunt, libero massa tincidunt sapien, vitae molestie justo nibh quis nisi.', '2025-09-22', 'SYSTEM'),
       (3, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, ante quis tincidunt tincidunt, libero massa tincidunt sapien, vitae molestie justo nibh quis nisi.', '2025-07-22', 'SYSTEM'),
       (3, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, ante quis tincidunt tincidunt, libero massa tincidunt sapien, vitae molestie justo nibh quis nisi.', '2025-03-22', 'SYSTEM'),
       (4, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, ante quis tincidunt tincidunt, libero massa tincidunt sapien, vitae molestie justo nibh quis nisi.', '2025-08-22', 'SYSTEM'),
       (4, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, ante quis tincidunt tincidunt, libero massa tincidunt sapien, vitae molestie justo nibh quis nisi.', '2025-06-22', 'SYSTEM'),
       (5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, ante quis tincidunt tincidunt, libero massa tincidunt sapien, vitae molestie justo nibh quis nisi.', '2025-05-22', 'SYSTEM'),
       (5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, ante quis tincidunt tincidunt, libero massa tincidunt sapien, vitae molestie justo nibh quis nisi.', '2025-04-22', 'SYSTEM'),
       (6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, ante quis tincidunt tincidunt, libero massa tincidunt sapien, vitae molestie justo nibh quis nisi.', '2025-02-22', 'SYSTEM'),
       (6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, ante quis tincidunt tincidunt, libero massa tincidunt sapien, vitae molestie justo nibh quis nisi.', '2025-08-22', 'SYSTEM');