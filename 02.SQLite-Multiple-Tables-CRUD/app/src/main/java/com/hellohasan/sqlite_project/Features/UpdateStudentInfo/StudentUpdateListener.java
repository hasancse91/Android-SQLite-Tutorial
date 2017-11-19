package com.hellohasan.sqlite_project.Features.UpdateStudentInfo;

import com.hellohasan.sqlite_project.Features.CreateStudent.Student;

public interface StudentUpdateListener {
    void onStudentInfoUpdated(Student student, int position);
}
