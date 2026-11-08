-- Insert three example students once; keep any students you add later.
INSERT INTO students (id, name, grade) VALUES
    (1, 'Jwan', 90),
    (2, 'Lina', 95),
    (3, 'Marwan', 87)
ON CONFLICT (id) DO NOTHING;

-- Keep the auto-generated ID above existing rows, including after restarts.
SELECT setval(pg_get_serial_sequence('students', 'id'), (SELECT MAX(id) FROM students));
