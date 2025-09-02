CREATE TABLE IF NOT EXISTS students
(
    id IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    kana_name VARCHAR(50) NOT NULL,
    nickname VARCHAR(50),
    email VARCHAR(50) NOT NULL,
    area VARCHAR(50),
    age INT,
    sex VARCHAR(10),
    remark TEXT,
    isDeleted BOOLEAN DEFAULT FALSE NOT NULL,
    is_canceled BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE IF NOT EXISTS students_courses
(
    id IDENTITY PRIMARY KEY,
    student_id INT NOT NULL,
    course_name VARCHAR(50) NOT NULL,
    course_start_at TIMESTAMP,
    course_end_at TIMESTAMP
);