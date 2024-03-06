# include "iGraphics.h"
#include<windows.h>
#include<MMsystem.h>
#include<stdlib.h>
#include<ctype.h>
#include<time.h>
#include<string.h>
using namespace std;

//NECESSARY CONTROL VARIABLES

int page_ind = 1;
int dis_ind = 0;
int input_ind = 0;
int num_event = 0;
int num_student = 0;
int num_pay = 0;
int error_flag = 0;
int num_stuquery = 0;
int num_defaulter = 0;

//MACROS DEFINITION

#define position_check(x, y, z) (x >= y&&x <= z)
#define MAX(a, b) ((a) > (b) ? (a) : (b))
#define MIN(a, b) ((a) < (b) ? (a) : (b))

//STAFF DATA

char staff_des[15][30] = {"Provost", "Ass. Provost (Establishment)", "Ass. Provost (Union)", "Ass. Provost (Mess)", "Senior Superviser", "Senior Cook", "Senior Office Ass.",  "Senior Office Ass.", "Senior Senior Guard", "Senior Senior Guard", "Senior Gardener", "Senior Sweeper"};
char staff_name[15][70] = {"Dr. Shakhawat", "Dr. Md. Iftekhar", "Dr. Md. Aman", "Dr. Shahnewaz", "Md. Matiur Rahman", "Md. Mozibur Rahman", "Md. Din Islam", "Md. Shahin Mia", "Md. Rezaul Haque", "Md. Nurul Islam", "Riton Mia", "Md. Emdadul Haque"};
char staff_contact[15][20] = {"01xxxxxxx", "01xxxxxxx", "01xxxxxxx", "01xxxxxxx", "01xxxxxxx", "01xxxxxxx", "01xxxxxxx", "01xxxxxxx", "01xxxxxxx", "01xxxxxxx", "01xxxxxxx", "01xxxxxxx"};

//EVENT DATA

typedef struct event_info{
    char name[80]; char place[80]; char begin[30]; char end[30];
}evinfo;
evinfo event_list[105];
evinfo event_query[105];

//STUDENT DATA

typedef struct stdent_info{
    char name[80]; char stuid[20]; char dept[20]; char status[20]; char phone[20];
    int pay_mon;
}stuinfo;
stuinfo student_list[1005];
stuinfo student_query[1005];

//PAYMENT DATA

char stid[20], mnth[20];
typedef struct defaulter{
    char stu_id[20]; int due_mon;
}defaulter;
defaulter de_list[1005];

//CODE FOR OTHER FUNCTIONS

void back(){

    error_flag = 0;
    if(page_ind == 241){
        for(int i = 0;i < 1000;i++){
            student_query[i].name[0] = '\0';
            student_query[i].stuid[0] = '\0';
            student_query[i].dept[0] = '\0';
            student_query[i].status[0] = '\0';
            student_query[i].phone[0] = '\0';
        }
    }
    if(page_ind == 32){
        for(int i = 0;i < 1000;i++){
            de_list[i].stu_id[0] = '\0';
            num_defaulter = 0;
        }
    }
    if(position_check(page_ind, 2, 6)) {dis_ind = 0; page_ind = 1;}
    if(position_check(page_ind, 41, 43)) {dis_ind = 0; page_ind = 4;}
    if(position_check(page_ind, 21, 25)) {dis_ind = 0; page_ind = 2;}
    if(position_check(page_ind, 241, 241)) {dis_ind = 0; page_ind = 24; num_stuquery = 0;}
    if(position_check(page_ind, 21, 21)) {dis_ind = 0, page_ind = 2;}
    if(position_check(page_ind, 31, 33)) {dis_ind = 0, page_ind = 3;}

}

void increament(int t) {dis_ind < t ? dis_ind+=1 : dis_ind+=0;}

void decreament() {dis_ind > 0 ? dis_ind-=1 : dis_ind-=0;}

void show_error()
{
    
    if(error_flag == 1) iText(105, 200, "Error: Incomplete Field", GLUT_BITMAP_9_BY_15);
    if(error_flag == 2) iText(105, 200, "Error: Name Used Before", GLUT_BITMAP_9_BY_15);
    if(error_flag == 3) iText(105, 200, "Error: Event not Found", GLUT_BITMAP_9_BY_15);
    if(error_flag == 4) iText(105, 170, "Error: Incomplete Field", GLUT_BITMAP_9_BY_15);
    if(error_flag == 5) iText(105, 170, "Error: Duplication of unique data", GLUT_BITMAP_9_BY_15);
    if(error_flag == 6) iText(105, 200, "Error: Schedule Conflict", GLUT_BITMAP_9_BY_15);
    if(error_flag == 7) iText(105, 200, "Error: Student Not Found", GLUT_BITMAP_9_BY_15);
    if(error_flag == 8) iText(105, 200, "Error: Incomplete Field", GLUT_BITMAP_9_BY_15);
    if(error_flag == 9) iText(105, 200, "Error: Student Not Found", GLUT_BITMAP_9_BY_15);
    if(error_flag == 10) iText(105, 200, "Error: Make Previous Payement First", GLUT_BITMAP_9_BY_15);
    if(error_flag == 11) iText(105, 200, "Error: Payment Already Made", GLUT_BITMAP_9_BY_15);
    if(error_flag == 12) iText(105, 60, "Error: Incomplete field(s)", GLUT_BITMAP_9_BY_15);
    if(error_flag == 13) iText(105, 60, "Error: Student Not Found", GLUT_BITMAP_9_BY_15);

}

void make_list()
{
    time_t tme; time(&tme); num_defaulter = 0;
    struct tm *sttm; sttm = localtime(&tme);
    int c = sttm->tm_year * 12 + sttm->tm_mon + 1; int j = 0;
    for(int i = 0;i < 1000;i++){
        if(student_list[i].pay_mon < c&&student_list[i].name[0] != '\0'){
            strcpy(de_list[j].stu_id, student_list[i].stuid);
            de_list[j].due_mon = c - student_list[i].pay_mon;
            j++;
        }
    }
    num_defaulter = j;
}

void iDraw()
{
    iClear();
    iShowBMP(0,0,"rsz_buet.bmp");
    show_error();
    if(page_ind == 1){
        
        //HOME PAGE DESIGN

        iSetColor(255, 0, 0);
        iText(70, 480, "Welcome to Hall Management System",GLUT_BITMAP_TIMES_ROMAN_24);
        iSetColor(0, 255, 0);
        iFilledRectangle(128, 60, 256, 40);
        iFilledRectangle(128, 130, 256, 40);
        iFilledRectangle(128, 200, 256, 40);
        iFilledRectangle(128, 270, 256, 40);
        iFilledRectangle(128, 340, 256, 40);
        iSetColor(0, 0, 0);
        iText(170, 356, "Student Management",GLUT_BITMAP_HELVETICA_18);
        iText(180, 286, "Dining Management",GLUT_BITMAP_HELVETICA_18);
        iText(178, 216, "Event Management",GLUT_BITMAP_HELVETICA_18);
        iText(212, 146, "Staff Info",GLUT_BITMAP_HELVETICA_18);
        iText(222, 76, "Credits",GLUT_BITMAP_HELVETICA_18);
    }
    else if(page_ind == 2){
        
        //STUDENT MANAGEMENT HOME PAGE DESIGN
        
        iSetColor(255, 0, 0);
        iText(155, 480, "Student Management",GLUT_BITMAP_TIMES_ROMAN_24);
        iSetColor(0,0,0);
        iRectangle(20, 20, 40, 20); iText(30, 27, "Back", GLUT_BITMAP_HELVETICA_10);
        iSetColor(0,255,0);
        iFilledRectangle(128, 130, 256, 40);
        iFilledRectangle(128, 200, 256, 40);
        iFilledRectangle(128, 270, 256, 40);
        iFilledRectangle(128, 340, 256, 40);
        iFilledRectangle(128, 60, 256, 40);
        iSetColor(0, 0, 0);
        iText(200, 286, "New Student",GLUT_BITMAP_HELVETICA_18);
        iText(196, 216, "View Students",GLUT_BITMAP_HELVETICA_18);
        iText(189, 146, "Delete Student",GLUT_BITMAP_HELVETICA_18);
        iText(184, 356, "Query Student",GLUT_BITMAP_HELVETICA_18);
        iText(194, 76, "Edit Student", GLUT_BITMAP_HELVETICA_18);
    }

    else if(page_ind == 25){

        //STUDENT MANAGEMENT EDIT STUDENT DESIGN

        iSetColor(255, 0, 0);
        iText(190, 480, "Edit Student",GLUT_BITMAP_TIMES_ROMAN_24);
        iSetColor(0,0,0);
        iRectangle(20, 20, 40, 20); iText(30, 27, "Back", GLUT_BITMAP_HELVETICA_10);
        iRectangle(50, 400, 50, 20); iText(55, 406, "Name", GLUT_BITMAP_HELVETICA_12);
        iRectangle(50, 350, 50, 20); iText(55, 356, "Stu ID", GLUT_BITMAP_HELVETICA_12);
        iRectangle(50, 300, 50, 20); iText(55, 306, "Dept", GLUT_BITMAP_HELVETICA_12);
        iRectangle(50, 250, 50, 20); iText(55, 256, "Status", GLUT_BITMAP_HELVETICA_12);
        iRectangle(50, 200, 50, 20); iText(55, 206, "Phone", GLUT_BITMAP_HELVETICA_12);
        iRectangle(50, 150, 50, 20); iText(55, 156, "Prev ID", GLUT_BITMAP_HELVETICA_12);
        iRectangle(100, 400, 300, 20); iRectangle(100, 350, 300, 20);
        iRectangle(100, 300, 300, 20); iRectangle(100, 250, 300, 20);
        iRectangle(100, 200, 300, 20); iRectangle(100, 150, 300, 20);
        iRectangle(205, 100, 72, 30); iText(215, 108, "Enter", GLUT_BITMAP_HELVETICA_18);
        iText(105, 406, student_list[1000].name, GLUT_BITMAP_HELVETICA_12);
        iText(105, 356, student_list[1000].stuid, GLUT_BITMAP_HELVETICA_12);
        iText(105, 306, student_list[1000].dept, GLUT_BITMAP_HELVETICA_12);
        iText(105, 256, student_list[1000].status, GLUT_BITMAP_HELVETICA_12);
        iText(105, 206, student_list[1000].phone, GLUT_BITMAP_HELVETICA_12);
        iText(105, 156, student_list[1001].stuid, GLUT_BITMAP_HELVETICA_12);
    }

    else if(page_ind == 23){
        
        //STUDENT MANAGEMENT NEW STUDENT DESIGN

        iSetColor(255, 0, 0);
        iText(190, 480, "New Student",GLUT_BITMAP_TIMES_ROMAN_24);
        iSetColor(0,0,0);
        iRectangle(20, 20, 40, 20); iText(30, 27, "Back", GLUT_BITMAP_HELVETICA_10);
        iRectangle(50, 400, 50, 20); iText(55, 406, "Name", GLUT_BITMAP_HELVETICA_12);
        iRectangle(50, 350, 50, 20); iText(55, 356, "Stu ID", GLUT_BITMAP_HELVETICA_12);
        iRectangle(50, 300, 50, 20); iText(55, 306, "Dept", GLUT_BITMAP_HELVETICA_12);
        iRectangle(50, 250, 50, 20); iText(55, 256, "Status", GLUT_BITMAP_HELVETICA_12);
        iRectangle(50, 200, 50, 20); iText(55, 206, "Phone", GLUT_BITMAP_HELVETICA_12);
        iRectangle(100, 400, 300, 20); iRectangle(100, 350, 300, 20);
        iRectangle(100, 300, 300, 20); iRectangle(100, 250, 300, 20);
        iRectangle(100, 200, 300, 20);
        iRectangle(205, 100, 72, 30); iText(215, 108, "Enter", GLUT_BITMAP_HELVETICA_18);
        iText(105, 406, student_list[num_student].name, GLUT_BITMAP_HELVETICA_12);
        iText(105, 356, student_list[num_student].stuid, GLUT_BITMAP_HELVETICA_12);
        iText(105, 306, student_list[num_student].dept, GLUT_BITMAP_HELVETICA_12);
        iText(105, 256, student_list[num_student].status, GLUT_BITMAP_HELVETICA_12);
        iText(105, 206, student_list[num_student].phone, GLUT_BITMAP_HELVETICA_12);
    }

    else if(page_ind == 22){

        //STUDENT MANAGEMENT VIEW STUDENTS DESIGN

        for(int v1 = 0;v1 < 6;v1++){
            if(dis_ind+v1 >= num_student) break;
            iText(10, 60+(5-v1)*60, student_list[dis_ind+v1].name, GLUT_BITMAP_HELVETICA_12);
            iText(200, 60+(5-v1)*60, student_list[dis_ind+v1].stuid, GLUT_BITMAP_HELVETICA_12);
            iText(270, 60+(5-v1)*60, student_list[dis_ind+v1].dept, GLUT_BITMAP_HELVETICA_12);
            iText(330, 60+(5-v1)*60, student_list[dis_ind+v1].status, GLUT_BITMAP_HELVETICA_12);
            iText(400, 60+(5-v1)*60, student_list[dis_ind+v1].phone, GLUT_BITMAP_HELVETICA_12);
        }
        iText(10, 400, "Name",GLUT_BITMAP_HELVETICA_18);
        iText(200, 400, "Stu ID",GLUT_BITMAP_HELVETICA_18);
        iText(270, 400, "Dept",GLUT_BITMAP_HELVETICA_18);
        iText(330, 400, "Status",GLUT_BITMAP_HELVETICA_18);
        iText(400, 400, "Phone",GLUT_BITMAP_HELVETICA_18);
        iSetColor(255, 0, 0);
        iFilledRectangle(492, 64, 10, 300);
        iText(165, 480, "Student Information",GLUT_BITMAP_TIMES_ROMAN_24);
        iText(70, 27, "Use UP/DOWN or drag along the scroll bar to navigate", GLUT_BITMAP_8_BY_13);
        iSetColor(0,0,0);
        iRectangle(20, 20, 40, 20); iText(30, 27, "Back", GLUT_BITMAP_HELVETICA_10);

    }

    else if(page_ind == 24){

        //QUERY STUDENT PAGE DESIGN
        iSetColor(255, 0, 0);
        iText(185, 480, "Query Student",GLUT_BITMAP_TIMES_ROMAN_24);
        iSetColor(0,0,0);
        iRectangle(20, 20, 40, 20); iText(30, 27, "Back", GLUT_BITMAP_HELVETICA_10);
        iRectangle(50, 400, 50, 20); iText(55, 406, "Name", GLUT_BITMAP_HELVETICA_12);
        iRectangle(50, 350, 50, 20); iText(55, 356, "Stu ID", GLUT_BITMAP_HELVETICA_12);
        iRectangle(50, 300, 50, 20); iText(55, 306, "Dept", GLUT_BITMAP_HELVETICA_12);
        iRectangle(50, 250, 50, 20); iText(55, 256, "Status", GLUT_BITMAP_HELVETICA_12);
        iRectangle(50, 200, 50, 20); iText(55, 206, "Phone", GLUT_BITMAP_HELVETICA_12);
        iRectangle(100, 400, 300, 20); iRectangle(100, 350, 300, 20);
        iRectangle(100, 300, 300, 20); iRectangle(100, 250, 300, 20);
        iRectangle(100, 200, 300, 20);
        iRectangle(205, 100, 72, 30); iText(215, 108, "Enter", GLUT_BITMAP_HELVETICA_18);
        iText(105, 406, student_list[1000].name, GLUT_BITMAP_HELVETICA_12);
        iText(105, 356, student_list[1000].stuid, GLUT_BITMAP_HELVETICA_12);
        iText(105, 306, student_list[1000].dept, GLUT_BITMAP_HELVETICA_12);
        iText(105, 256, student_list[1000].status, GLUT_BITMAP_HELVETICA_12);
        iText(105, 206, student_list[1000].phone, GLUT_BITMAP_HELVETICA_12);
    }

    else if(page_ind == 241){
        
        //QUERY STUDENT RESULT PAGE DESIGN
        for(int v1 = 0;v1 < 6;v1++){
            if(dis_ind+v1 >= num_stuquery) break;
            iText(10, 60+(5-v1)*60, student_query[dis_ind+v1].name, GLUT_BITMAP_HELVETICA_12);
            iText(200, 60+(5-v1)*60, student_query[dis_ind+v1].stuid, GLUT_BITMAP_HELVETICA_12);
            iText(270, 60+(5-v1)*60, student_query[dis_ind+v1].dept, GLUT_BITMAP_HELVETICA_12);
            iText(330, 60+(5-v1)*60, student_query[dis_ind+v1].status, GLUT_BITMAP_HELVETICA_12);
            iText(400, 60+(5-v1)*60, student_query[dis_ind+v1].phone, GLUT_BITMAP_HELVETICA_12);
        }
        iText(10, 400, "Name",GLUT_BITMAP_HELVETICA_18);
        iText(200, 400, "Stu ID",GLUT_BITMAP_HELVETICA_18);
        iText(270, 400, "Dept",GLUT_BITMAP_HELVETICA_18);
        iText(330, 400, "Status",GLUT_BITMAP_HELVETICA_18);
        iText(400, 400, "Phone",GLUT_BITMAP_HELVETICA_18);
        iSetColor(255, 0, 0);
        iFilledRectangle(492, 64, 10, 300);
        iText(165, 480, "Query Result",GLUT_BITMAP_TIMES_ROMAN_24);
        iText(70, 27, "Use UP/DOWN or drag along the scroll bar to navigate", GLUT_BITMAP_8_BY_13);
        iSetColor(0,0,0);
        iRectangle(20, 20, 40, 20); iText(30, 27, "Back", GLUT_BITMAP_HELVETICA_10);
    }

    if(page_ind == 21){

        //DELETE STUDENT PAGE DESIGN
        iSetColor(255, 0, 0);
        iText(190, 480, "Delete Student",GLUT_BITMAP_TIMES_ROMAN_24);
        iSetColor(0,0,0);
        iRectangle(20, 20, 40, 20); iText(30, 27, "Back", GLUT_BITMAP_HELVETICA_10);
        iRectangle(50, 400, 50, 20); iText(55, 406, "Stuid", GLUT_BITMAP_HELVETICA_12);
        iRectangle(100, 400, 300, 20);
        iRectangle(205, 100, 72, 30); iText(215, 108, "Enter", GLUT_BITMAP_HELVETICA_18);
        iText(105, 406, student_list[1000].stuid, GLUT_BITMAP_HELVETICA_12);

    }

    else if(page_ind == 3){

        //DINING MANAGEMENT HOME PAGE DESIGN

        iSetColor(255, 0, 0);
        iText(155, 480, "Dining Management",GLUT_BITMAP_TIMES_ROMAN_24);
        iSetColor(0,0,0);
        iRectangle(20, 20, 40, 20); iText(30, 27, "Back", GLUT_BITMAP_HELVETICA_10);
        iSetColor(0, 255, 0);
        iFilledRectangle(128, 270, 256, 40);
        iFilledRectangle(128, 200, 256, 40);
        // iFilledRectangle(128, 130, 256, 40);
        iSetColor(0, 0, 0);
        iText(198, 286, "Make Payment",GLUT_BITMAP_HELVETICA_18);
        iText(158, 216, "List of Defaulter Student",GLUT_BITMAP_HELVETICA_18);
        // iText(212, 146, "Staff Info",GLUT_BITMAP_HELVETICA_18);

    }

    else if(page_ind == 31){

        //DINING MANAGEMENT MAKE PAYMENT PAGE DESIGN

        iSetColor(255, 0, 0);
        iText(155, 488, "Make Payment",GLUT_BITMAP_TIMES_ROMAN_24);
        iSetColor(0,0,0);
        iRectangle(20, 20, 40, 20); iText(30, 27, "Back", GLUT_BITMAP_HELVETICA_10);        
        iRectangle(50, 400, 50, 20); iText(55, 406, "Stu ID", GLUT_BITMAP_HELVETICA_12);
        iRectangle(50, 350, 100, 20); iText(55, 356, "MM.YYYY", GLUT_BITMAP_HELVETICA_12);
        iRectangle(100, 400, 300, 20); iRectangle(150, 350, 250, 20);
        iText(105, 406, stid, GLUT_BITMAP_HELVETICA_12);
        iText(155, 356, mnth, GLUT_BITMAP_HELVETICA_12);
        iRectangle(205, 100, 72, 30); iText(215, 108, "Enter", GLUT_BITMAP_HELVETICA_18);

    }

    else if(page_ind == 32){

        //DINING MANAGEMENT DEFAULTER STUDENT LIST PAGE DESIGN

        make_list(); //Function to build the defaulter list
        for(int v1 = 0;v1 < 6;v1++){
            if(de_list[dis_ind+v1].stu_id[0] == '\0') break;
            iText(10, 60+(5-v1)*60, de_list[dis_ind+v1].stu_id, GLUT_BITMAP_HELVETICA_12);
            char temp[20]; sprintf(temp, "%d", de_list[dis_ind+v1].due_mon);
            iText(330, 60+(5-v1)*60, temp, GLUT_BITMAP_HELVETICA_12);
        }
        iText(10, 400, "Student ID",GLUT_BITMAP_HELVETICA_18);
        iText(290, 400, "Number of Due Payments",GLUT_BITMAP_HELVETICA_18);
        iSetColor(255, 0, 0);
        iFilledRectangle(492, 64, 10, 300);
        iText(165, 480, "Defaulter Information",GLUT_BITMAP_TIMES_ROMAN_24);
        iText(70, 27, "Use UP/DOWN or drag along the scroll bar to navigate", GLUT_BITMAP_8_BY_13);
        iSetColor(0,0,0);
        iRectangle(20, 20, 40, 20); iText(30, 27, "Back", GLUT_BITMAP_HELVETICA_10);

    }

    else if(page_ind == 4){

        //EVENT MANAGEMENT HOME PAGE DESIGN

        iSetColor(255, 0, 0);
        iText(165, 480, "Event Management",GLUT_BITMAP_TIMES_ROMAN_24);
        iSetColor(0,0,0);
        iRectangle(20, 20, 40, 20); iText(30, 27, "Back", GLUT_BITMAP_HELVETICA_10);
        iSetColor(0,255,0);
        iFilledRectangle(128, 130, 256, 40);
        iFilledRectangle(128, 200, 256, 40);
        iFilledRectangle(128, 270, 256, 40);
        iSetColor(0, 0, 0);
        iText(210, 286, "New Event",GLUT_BITMAP_HELVETICA_18);
        iText(206, 216, "View Events",GLUT_BITMAP_HELVETICA_18);
        iText(199, 146, "Delete Event",GLUT_BITMAP_HELVETICA_18);

    }

    else if(page_ind == 41){
        
        //EVENT MANAGEMENT DELETE EVENT DESIGN
        
        iSetColor(255, 0, 0);
        iText(185, 480, "Delete Event",GLUT_BITMAP_TIMES_ROMAN_24);
        iSetColor(0,0,0);
        iRectangle(20, 20, 40, 20); iText(30, 27, "Back", GLUT_BITMAP_HELVETICA_10);
        iRectangle(50, 400, 50, 20); iText(55, 406, "Name", GLUT_BITMAP_HELVETICA_12); iRectangle(100, 400, 300, 20);
        iRectangle(205, 100, 72, 30); iText(215, 108, "Enter", GLUT_BITMAP_HELVETICA_18);
        iText(105, 406, event_list[100].name, GLUT_BITMAP_HELVETICA_12);

    }

    else if(page_ind == 42){
        
        //EVENT MANAGEMENT VIEW EVENTS DESIGN
        
        for(int v1 = 0;v1 < 6;v1++){
            if(dis_ind+v1 >= num_event) break;
            iText(30, 60+(5-v1)*60, event_list[dis_ind+v1].name, GLUT_BITMAP_HELVETICA_12);
            iText(220, 60+(5-v1)*60, event_list[dis_ind+v1].place, GLUT_BITMAP_HELVETICA_12);
            iText(300, 60+(5-v1)*60, event_list[dis_ind+v1].begin, GLUT_BITMAP_HELVETICA_12);
            iText(400, 60+(5-v1)*60, event_list[dis_ind+v1].end, GLUT_BITMAP_HELVETICA_12);
        }
        iText(30, 400, "Name",GLUT_BITMAP_HELVETICA_18);
        iText(220, 400, "Place",GLUT_BITMAP_HELVETICA_18);
        iText(300, 400, "Start Time",GLUT_BITMAP_HELVETICA_18);
        iText(400, 400, "End Time",GLUT_BITMAP_HELVETICA_18);
        iSetColor(255, 0, 0);
        iFilledRectangle(492, 64, 10, 300);
        iText(165, 480, "Event Information",GLUT_BITMAP_TIMES_ROMAN_24);
        iText(70, 27, "Use UP/DOWN or drag along the scroll bar to navigate", GLUT_BITMAP_8_BY_13);
        iSetColor(0,0,0);
        iRectangle(20, 20, 40, 20); iText(30, 27, "Back", GLUT_BITMAP_HELVETICA_10);

    }

    else if(page_ind == 43){
        
        //EVENT MANAGEMENT NEW EVENT DESIGN
        
        iSetColor(255, 0, 0);
        iText(190, 480, "New Event",GLUT_BITMAP_TIMES_ROMAN_24);
        iSetColor(0,0,0);
        iRectangle(20, 20, 40, 20); iText(30, 27, "Back", GLUT_BITMAP_HELVETICA_10);
        iRectangle(50, 400, 50, 20); iText(55, 406, "Name", GLUT_BITMAP_HELVETICA_12);
        iRectangle(50, 350, 50, 20); iText(55, 356, "Place", GLUT_BITMAP_HELVETICA_12);
        iRectangle(50, 300, 150, 20); iText(55, 306, "Start(DD.MM.YYYY.HH)", GLUT_BITMAP_HELVETICA_12);
        iRectangle(50, 250, 150, 20); iText(55, 256, "End(DD.MM.YYYY.HH)", GLUT_BITMAP_HELVETICA_12);
        iRectangle(100, 400, 300, 20); iRectangle(100, 350, 300, 20);
        iRectangle(200, 300, 200, 20); iRectangle(200, 250, 200, 20);
        iRectangle(205, 100, 72, 30); iText(215, 108, "Enter", GLUT_BITMAP_HELVETICA_18);
        iText(105, 406, event_list[num_event].name, GLUT_BITMAP_HELVETICA_12);
        iText(105, 356, event_list[num_event].place, GLUT_BITMAP_HELVETICA_12);
        iText(205, 306, event_list[num_event].begin, GLUT_BITMAP_HELVETICA_12);
        iText(205, 256, event_list[num_event].end, GLUT_BITMAP_HELVETICA_12);

    }

    else if(page_ind == 5){

        //STAFF INFO HOME PAGE DESIGN
        
        for(int v1 = 0;v1 < 6;v1++){
            iText(30, 60+(5-v1)*60, staff_des[dis_ind+v1], GLUT_BITMAP_HELVETICA_12);
            iText(190, 60+(5-v1)*60, staff_name[dis_ind+v1], GLUT_BITMAP_HELVETICA_12);
            iText(345, 60+(5-v1)*60, staff_contact[dis_ind+v1], GLUT_BITMAP_HELVETICA_12);
        }
        iText(30, 400, "Designation",GLUT_BITMAP_HELVETICA_18);
        iText(190, 400, "Name",GLUT_BITMAP_HELVETICA_18);
        iText(345, 400, "Contact Number",GLUT_BITMAP_HELVETICA_18);
        iSetColor(255, 0, 0);
        iFilledRectangle(492, 64, 10, 300);
        iText(165, 480, "Staff Information",GLUT_BITMAP_TIMES_ROMAN_24);
        iText(70, 27, "Use UP/DOWN or drag along the scroll bar to navigate", GLUT_BITMAP_8_BY_13);
        iSetColor(0,0,0);
        iRectangle(20, 20, 40, 20); iText(30, 27, "Back", GLUT_BITMAP_HELVETICA_10);

    }

    else if(page_ind == 6){
        
        //CREDITS PAGE DESIGN
        
        iSetColor(255, 0, 0);
        iText(230, 480, "Credits",GLUT_BITMAP_TIMES_ROMAN_24);
        iSetColor(0,0,0);
        iRectangle(20, 20, 40, 20); iText(30, 27, "Back", GLUT_BITMAP_HELVETICA_10);
        iText(40, 420, "Created by: Md. Fahim Abrar", GLUT_BITMAP_HELVETICA_18);
        iText(40, 390, "ID: 2205070", GLUT_BITMAP_HELVETICA_18);
        iText(40, 360, "Computer Science and Engineering (CSE)", GLUT_BITMAP_HELVETICA_18);
        iText(40, 330, "Bangladesh University of Engineering and Technology", GLUT_BITMAP_HELVETICA_18);
        iText(40, 300, "E-mail: mdfaabrar257@gmail.com", GLUT_BITMAP_HELVETICA_18);
        iText(40, 240, "Supervised by: Dr. Md. Mostofa Akbar", GLUT_BITMAP_HELVETICA_18);
        iText(40, 210, "Professor, CSE Department", GLUT_BITMAP_HELVETICA_18);
        iText(40, 180, "Bangladesh University of Engineering and Technology", GLUT_BITMAP_HELVETICA_18);
        iText(40, 150, "E-mail: mostofa@cse.buet.ac.bd", GLUT_BITMAP_HELVETICA_18);
        iText(40, 90, "Published: 27 December, 2023", GLUT_BITMAP_HELVETICA_18);
    }
}

/*
function iMouseMove() is called when the user presses and drags the mouse.
(mx, my) is the position where the mouse pointer is.
*/

void iMouseMove(int mx, int my)
{
    if(page_ind == 5){
        
        //STAFF INFO BRAG BAR CONTROL

        if(position_check(mx, 492, 502)&&position_check(my, 64, 364)){
            dis_ind = (394-my)/50;
        }

    }

    if(page_ind == 42){
        
        //VIEW EVENTS DRAG BAR CONTROL

        if(position_check(mx, 492, 502)&&position_check(my, 64, 364)){
            dis_ind = (394-my)/(300 / MAX(1, num_event-6));
        }
    }

    if(page_ind == 22){
        
        //VIEW STUDENTS DRAG BAR CONTROL

        if(position_check(mx, 492, 502)&&position_check(my, 64, 364)){
            dis_ind = (394-my)/(300 / MAX(1, num_student-6));
        }
    }

    if(page_ind == 241){
        
        //VIEW STUDENT QUERY RESULT DRAG BAR CONTROL

        if(position_check(mx, 492, 502)&&position_check(my, 64, 364)){
            dis_ind = (394-my)/(300 / MAX(1, num_stuquery-6));
        }
    }

    if(page_ind == 32){

        //VIEW STUDENT QUERY RESULT DRAG BAR CONTROL

        if(position_check(mx, 492, 502)&&position_check(my, 64, 364)){
            dis_ind = (394-my)/(300 / MAX(1, num_defaulter-6));
        }

    }
}

/*
function iMouse() is called when the user presses/releases the mouse.
(mx, my) is the position where the mouse pointer is.
*/

void iMouse(int button, int state, int mx, int my)
{
    
    if(page_ind != 1)
    {
        
        //GOING BACK CONTROL

        if(button == GLUT_LEFT_BUTTON && state == GLUT_DOWN){
            if(position_check(mx, 20, 60)&&position_check(my, 20, 40)) back();
        }

    }
    
    if(page_ind == 1){
        
        //HOME PAGE CONTROL
        
        if(button == GLUT_LEFT_BUTTON && state == GLUT_DOWN){
            if(position_check(mx, 128, 384)&&position_check(my, 60, 100)){
                page_ind = 6;
            }
            if(position_check(mx, 128, 384)&&position_check(my, 130, 170)){
                page_ind = 5;
            }
            if(position_check(mx, 128, 384)&&position_check(my, 200, 240)){
                page_ind = 4;
            }
            if(position_check(mx, 128, 384)&&position_check(my,270, 310)){
                page_ind = 3;
            }
            if(position_check(mx, 128, 384)&&position_check(my, 340, 380)){
                page_ind = 2;
            }
        }

    }

    else if(page_ind == 3){
        
        //DINING MANAGEMENT HOME PAGE CONTROL
        if(button == GLUT_LEFT_BUTTON && state == GLUT_DOWN){
            // if(position_check(mx, 128, 384)&&position_check(my, 130, 170)){
            //     page_ind = 33;
            // }
            if(position_check(mx, 128, 384)&&position_check(my, 200, 240)){
                page_ind = 32;
            }
            if(position_check(mx, 128, 384)&&position_check(my,270, 310)){
                page_ind = 31;
            }
        }

    }

    else if(page_ind == 31){

        //MAKE PAYMENT PAGE CONTROL

        if(button == GLUT_LEFT_BUTTON && state == GLUT_DOWN){
            if(position_check(mx, 100, 400)&&position_check(my, 400, 420)){
                input_ind = 1; 
            }
            else if(position_check(mx, 150, 400)&&position_check(my, 350, 370)){
                input_ind = 2; 
            }
            else if(position_check(mx, 205, 277)&&position_check(my, 100, 130)){
                int temp_error_flag = 0; int k = 0;
                if(strlen(stid) == 0||strlen(mnth) != 7) temp_error_flag = error_flag = 8;
                if(temp_error_flag == 0){
                    for(;k < num_student;k++){
                        if(strcmp(student_list[k].stuid, stid) == 0) break;
                    }   
                    if(k == num_student) temp_error_flag = error_flag = 9;
                }
                if(temp_error_flag == 0){
                    time_t t; int i, mm, yy;
                    time(&t);
                    struct tm *ltime = localtime(&t);
                    i = ltime->tm_mon + 1 + (ltime->tm_year) * 12;
                    sscanf(mnth, "%d.%d", &mm, &yy);
                    if(mm+(yy- 1900)*12 > student_list[k].pay_mon+1) temp_error_flag = error_flag = 10;
                    else if(mm+(yy - 1900)*12 < student_list[k].pay_mon+1) temp_error_flag = error_flag = 11;
                }
                if(temp_error_flag == 0){
                    student_list[k].pay_mon++; 
                    FILE *fp = fopen("text_files/students.txt", "w");
                    for(int i = 0;i < num_student;i++){
                        if(strlen(student_list[i].name) == 0) continue;
                        fputs(student_list[i].name, fp); fputs("\n", fp);
                        fputs(student_list[i].stuid, fp); fputs("\n", fp);
                        fputs(student_list[i].dept, fp); fputs("\n", fp);
                        fputs(student_list[i].status, fp); fputs("\n", fp);
                        fputs(student_list[i].phone, fp); fputs("\n", fp);
                        char temp[20]; sprintf(temp, "%d", student_list[i].pay_mon);
                        fputs(temp, fp); fputs("\n", fp);
                    }
                    fclose(fp);
                    error_flag = 0; page_ind = 3;
                    stid[0] = '\0';
                    mnth[0] = '\0';
                }
            }
        }
    }

    else if(page_ind == 4){
        
        //EVENT MANAGEMENT HOME PAGE CONTROL
        
        if(button == GLUT_LEFT_BUTTON && state == GLUT_DOWN){
            if(position_check(mx, 128, 384)&&position_check(my, 130, 170)){
                page_ind = 41;
            }
            if(position_check(mx, 128, 384)&&position_check(my, 200, 240)){
                page_ind = 42;
            }
            if(position_check(mx, 128, 384)&&position_check(my,270, 310)){
                page_ind = 43;
            }
        }

    }

    else if(page_ind == 2){
        
        //STUDENT MANAGEMENT HOME PAGE CONTROL
        
        if(button == GLUT_LEFT_BUTTON && state == GLUT_DOWN){
            if(position_check(mx, 128, 384)&&position_check(my, 130, 170)){
                page_ind = 21;
            }
            if(position_check(mx, 128, 384)&&position_check(my, 200, 240)){
                page_ind = 22;
            }
            if(position_check(mx, 128, 384)&&position_check(my,270, 310)){
                page_ind = 23;
            }
            if(position_check(mx, 128, 384)&&position_check(my,340, 370)){
                page_ind = 24;
            }
            if(position_check(mx, 128, 384)&&position_check(my, 60, 100)){
                page_ind = 25;
            }
        }

    }

    if(page_ind == 23){
        
        //NEW STUDENT PAGE CONTROL
        
        if(button == GLUT_LEFT_BUTTON && state == GLUT_DOWN){
            
            //INPUT FIELD SELECTION

            if(position_check(mx, 100, 400)&&position_check(my, 400, 420)){
                input_ind = 1; 
            }
            else if(position_check(mx, 100, 400)&&position_check(my, 350, 370)){
                input_ind = 2; 
            }
            else if(position_check(mx, 100, 400)&&position_check(my, 300, 320)){
                input_ind = 3; 
            }
            else if(position_check(mx, 100, 400)&&position_check(my, 250, 270)){
                input_ind = 4; 
            }
            else if(position_check(mx, 100, 400)&&position_check(my, 200, 220)){
                input_ind = 5; 
            }

            //FINAL VERIFICATION AND CONFIRMAITON

            else if(position_check(mx, 205, 277)&&position_check(my, 100, 130)){
                int temp_error_flag = 0;
                if(strlen(student_list[num_student].name) == 0||strlen(student_list[num_student].stuid) != 7||strlen(student_list[num_student].dept) == 0||strlen(student_list[num_student].status) == 0||strlen(student_list[num_student].phone) != 11){
                    temp_error_flag = error_flag = 4;
                }
                if(temp_error_flag == 0){
                    int i = 0;
                    for(;i < num_student;i++){
                        if(strcmp(student_list[i].stuid, student_list[num_student].stuid) == 0) break;
                        if(strcmp(student_list[i].phone, student_list[num_student].phone) == 0) break;
                    }
                    if(i < num_student) temp_error_flag = error_flag = 5;
                }
                if(temp_error_flag == 0){
                    FILE *fp = fopen("text_files/students.txt", "a");
                    fputs(student_list[num_student].name, fp); fputs("\n", fp);
                    fputs(student_list[num_student].stuid, fp); fputs("\n", fp);
                    fputs(student_list[num_student].dept, fp); fputs("\n", fp);
                    fputs(student_list[num_student].status, fp); fputs("\n", fp);
                    fputs(student_list[num_student].phone, fp); fputs("\n", fp);
                    time_t t; int mm, yy;
                    time(&t);
                    struct tm *ltime = localtime(&t);
                    student_list[num_student].pay_mon = ltime->tm_mon + (ltime->tm_year) * 12;
                    
                    char temp[20]; sprintf(temp, "%d", student_list[num_student].pay_mon);
                    fputs(temp, fp); fputs("\n", fp);
                    fclose(fp);
                    student_list[1000].name[0] = '\0';
                    student_list[1000].status[0] = '\0';
                    student_list[1000].stuid[0] = '\0';
                    student_list[1000].dept[0] = '\0';
                    student_list[1000].phone[0] = '\0';
                    page_ind = 2; num_student++; error_flag = 0;
                }
            }
            else input_ind = 0;
        }

    }

    if(page_ind == 25){

        //STUDENT MANAGEMENT EDIT STUDENT PAGE CONTROL

        if(button == GLUT_LEFT_BUTTON && state == GLUT_DOWN){
            
            //INPUT FIELD SELECTION

            if(position_check(mx, 100, 400)&&position_check(my, 400, 420)){
                input_ind = 1; 
            }
            else if(position_check(mx, 100, 400)&&position_check(my, 350, 370)){
                input_ind = 2; 
            }
            else if(position_check(mx, 100, 400)&&position_check(my, 300, 320)){
                input_ind = 3; 
            }
            else if(position_check(mx, 100, 400)&&position_check(my, 250, 270)){
                input_ind = 4; 
            }
            else if(position_check(mx, 100, 400)&&position_check(my, 200, 220)){
                input_ind = 5; 
            }
            else if(position_check(mx, 100, 400)&&position_check(my, 150, 170)){
                input_ind = 6; 
            }

            //FINAL VERIFICATION AND CONFIRMAITON

            else if(position_check(mx, 205, 277)&&position_check(my, 100, 130)){
                int k = 0, temp_error_flag = 0;
                if(strlen(student_list[1001].stuid) != 7||strlen(student_list[1000].name) == 0||strlen(student_list[1000].stuid) != 7||strlen(student_list[1000].dept) == 0||strlen(student_list[1000].status) == 0||strlen(student_list[1000].phone) != 11){
                    temp_error_flag = error_flag = 12;
                    printf("%s %d\n", student_list[1000].status, strlen(student_list[1000].status));
                }
                if(temp_error_flag == 0){
                    for(k = 0;k < num_student;k++){
                        if(strcmp(student_list[k].stuid, student_list[1001].stuid) == 0) break;
                        //if(strcmp(student_list[k].phone, student_list[1001].phone) == 0) {k = num_student; break;}
                    }
                }
                if(k == num_student) temp_error_flag = error_flag = 13;
                if(temp_error_flag == 0){
                    strcpy(student_list[k].name, student_list[1000].name);
                    strcpy(student_list[k].stuid, student_list[1000].stuid);
                    strcpy(student_list[k].dept, student_list[1000].dept);
                    strcpy(student_list[k].status, student_list[1000].status);
                    strcpy(student_list[k].phone, student_list[1000].phone);
                    FILE *fp = fopen("text_files/students.txt", "w");
                    for(int i = 0;i < num_student;i++){
                        if(strlen(student_list[i].name) == 0) continue;
                        fputs(student_list[i].name, fp); fputs("\n", fp);
                        fputs(student_list[i].stuid, fp); fputs("\n", fp);
                        fputs(student_list[i].dept, fp); fputs("\n", fp);
                        fputs(student_list[i].status, fp); fputs("\n", fp);
                        fputs(student_list[i].phone, fp); fputs("\n", fp);
                        char temp[20]; sprintf(temp, "%d", student_list[i].pay_mon);
                        fputs(temp, fp); fputs("\n", fp);
                    }
                    fclose(fp);
                    student_list[1000].name[0] = '\0';
                    student_list[1000].stuid[0] = '\0';
                    student_list[1000].phone[0] = '\0';
                    student_list[1000].dept[0] = '\0';
                    student_list[1000].status[0] = '\0';
                    student_list[1001].stuid[0] = '\0';
                    error_flag = 0; page_ind = 2;
                }
            }
            else input_ind = 0;
        }
    }

    if(page_ind == 24){
        
        //QUERY STUDENT PAGE CONTROL
        
        if(button == GLUT_LEFT_BUTTON && state == GLUT_DOWN){
            
            //INPUT FIELD SELECTION

            if(position_check(mx, 100, 400)&&position_check(my, 400, 420)){
                input_ind = 1; 
            }
            else if(position_check(mx, 100, 400)&&position_check(my, 350, 370)){
                input_ind = 2; 
            }
            else if(position_check(mx, 100, 400)&&position_check(my, 300, 320)){
                input_ind = 3; 
            }
            else if(position_check(mx, 100, 400)&&position_check(my, 250, 270)){
                input_ind = 4; 
            }
            else if(position_check(mx, 100, 400)&&position_check(my, 200, 220)){
                input_ind = 5; 
            }

            //FINAL VERIFICATION AND CONFIRMAITON

            else if(position_check(mx, 205, 277)&&position_check(my, 100, 130)){
                for(int i = 0;i < 1000;i++){
                    student_query[i].name[0] = '\0';
                    student_query[i].stuid[0] = '\0';
                    student_query[i].status[0] = '\0';
                    student_query[i].dept[0] = '\0';
                    student_query[i].phone[0] = '\0';
                }
                for(int i = 0;i < num_student;i++){
                    if(strstr(student_list[i].name, student_list[1000].name) != NULL&&strstr(student_list[i].status, student_list[1000].status) != NULL&&strstr(student_list[i].stuid, student_list[1000].stuid) != NULL&&strstr(student_list[i].dept, student_list[1000].dept) != NULL&&strstr(student_list[i].phone, student_list[1000].phone) != NULL){
                        strcpy(student_query[num_stuquery].name, student_list[i].name);
                        strcpy(student_query[num_stuquery].status, student_list[i].status);
                        strcpy(student_query[num_stuquery].stuid, student_list[i].stuid);
                        strcpy(student_query[num_stuquery].dept, student_list[i].dept);
                        strcpy(student_query[num_stuquery].phone, student_list[i].phone);
                        num_stuquery++;
                    }
                }
                page_ind = 241;
                student_list[1000].name[0] = '\0';
                student_list[1000].stuid[0] = '\0';
                student_list[1000].dept[0] = '\0';
                student_list[1000].status[0] = '\0';
                student_list[1000].phone[0] = '\0';
            }
            else input_ind = 0;
        }

    }

    if(page_ind == 21){
        
        //DELTE STUDENT INPUT CONTROL

        if(button == GLUT_LEFT_BUTTON && state == GLUT_DOWN){
            if(position_check(mx, 100, 400)&&position_check(my, 400, 420)){
                input_ind = 1; 
            }
            else if(position_check(mx, 205, 277)&&position_check(my, 100, 130)){
                int k = 0;
                for(;k <num_student;k++){
                    if(strcmp(student_list[1000].stuid, student_list[k].stuid) == 0) break;
                }
                if(k == num_student) error_flag = 7;
                if(k < num_student) {
                    student_list[k].name[0] = '\0';
                    FILE *fp = fopen("text_files/students.txt", "w");
                    for(int i = 0;i < num_student;i++){
                        if(strlen(student_list[i].name) == 0) continue;
                        fputs(student_list[i].name, fp); fputs("\n", fp);
                        fputs(student_list[i].stuid, fp); fputs("\n", fp);
                        fputs(student_list[i].dept, fp); fputs("\n", fp);
                        fputs(student_list[i].status, fp); fputs("\n", fp);
                        fputs(student_list[i].phone, fp); fputs("\n", fp);
                        char temp[20]; sprintf(temp, "%d", student_list[i].pay_mon);
                        fputs(temp, fp); fputs("\n", fp);
                    }
                    fclose(fp);
                    fp = fopen("text_files/students.txt", "r");
                    int i = 0;
                    while(1){
                        if(fgets(student_list[i].name, 80, fp) == NULL) break;
                        fgets(student_list[i].stuid, 20, fp);
                        fgets(student_list[i].dept, 20, fp);
                        fgets(student_list[i].status, 20, fp);
                        fgets(student_list[i].phone, 20, fp);
                        char temp[20];
                        fgets(temp, 20, fp);
                        student_list[i].name[strlen(student_list[i].name)-1] = '\0';
                        student_list[i].stuid[strlen(student_list[i].stuid)-1] = '\0';
                        student_list[i].dept[strlen(student_list[i].dept)-1] = '\0';
                        student_list[i].status[strlen(student_list[i].status)-1] = '\0';
                        student_list[i].phone[strlen(student_list[i].phone)-1] = '\0';
                        temp[strlen(temp)-1] = '\0'; student_list[i].pay_mon = atoi(temp);
                        i++;
                    }
                    fclose(fp);
                    student_list[1000].name[0] = student_list[num_student-1].name[0] = '\0';
                    student_list[1000].stuid[0] = student_list[num_student-1].stuid[0] = '\0';
                    student_list[1000].dept[0] = student_list[num_student-1].dept[0] = '\0';
                    student_list[1000].status[0] = student_list[num_student-1].status[0] = '\0';
                    student_list[1000].phone[0] = student_list[num_student-1].phone[0] = '\0';
                    page_ind = 2; error_flag = 0; num_student--;
                }
            }
            else input_ind = 0;
        }
    }

    if(page_ind == 43){
        
        //NEW EVENT PAGE CONTROL
        
        if(button == GLUT_LEFT_BUTTON && state == GLUT_DOWN){
            
            //INPUT FIELD SELECTION
            
            if(position_check(mx, 100, 400)&&position_check(my, 400, 420)){
                input_ind = 1; 
            }
            else if(position_check(mx, 100, 400)&&position_check(my, 350, 370)){
                input_ind = 2; 
            }
            else if(position_check(mx, 200, 400)&&position_check(my, 300, 320)){
                input_ind = 3; 
            }
            else if(position_check(mx, 200, 400)&&position_check(my, 250, 270)){
                input_ind = 4; 
            }

            //FINAL VERIFICATION AND CONFIRMATION

            else if(position_check(mx, 205, 277)&&position_check(my, 100, 130)){
                int temp_error_flag = 0;
                if(!strlen(event_list[num_event].name)||!strlen(event_list[num_event].place)||strlen(event_list[num_event].begin) != 13||strlen(event_list[num_event].end) != 13){
                    printf("cc\n");
                    error_flag = temp_error_flag = 1;
                }
                if(temp_error_flag == 0){
                    printf("dd\n");
                    int i;
                    for(i = 0;i < num_event;i++){
                        if(strcmp(event_list[i].name, event_list[num_event].name) == 0) break;
                    }
                    if(i < num_event) error_flag = temp_error_flag = 2;
                }
                if(temp_error_flag == 0){
                    printf("ee\n");
                    struct tm stc, enc; int yy, dd, mm, hh, i;
                    time_t t_begin, t_end;
                    sscanf(event_list[num_event].begin, "%d.%d.%d.%d", &dd, &mm, &yy, &hh);
                    stc.tm_hour = hh, stc.tm_mday = dd, stc.tm_mon = mm-1, stc.tm_year = yy - 1900;
                    stc.tm_isdst = -1, stc.tm_min = stc.tm_sec = 0; t_begin = mktime(&stc);

                    sscanf(event_list[num_event].end, "%d.%d.%d.%d", &dd, &mm, &yy, &hh);
                    stc.tm_hour = hh, stc.tm_mday = dd, stc.tm_mon = mm-1, stc.tm_year = yy - 1900;
                    stc.tm_isdst = -1, stc.tm_min = stc.tm_sec = 0; t_end = mktime(&stc);

                    for(i = 0;i < num_event;i++){
                        sscanf(event_list[i].begin, "%d.%d.%d.%d", &dd, &mm, &yy, &hh);
                        stc.tm_hour = hh, stc.tm_mday = dd, stc.tm_mon = mm-1, stc.tm_year = yy - 1900;
                        stc.tm_isdst = -1, stc.tm_min = stc.tm_sec = 0;

                        sscanf(event_list[i].end, "%d.%d.%d.%d", &dd, &mm, &yy, &hh);
                        enc.tm_hour = hh, enc.tm_mday = dd, enc.tm_mon = mm-1, enc.tm_year = yy - 1900;
                        enc.tm_isdst = -1, enc.tm_min = enc.tm_sec = 0;
                        printf("%lf %lf\n", difftime(mktime(&stc), t_end) , difftime(t_begin, mktime(&enc)));
                        if(!(difftime(mktime(&stc), t_end) > 0.0 || difftime(t_begin, mktime(&enc)) > 0.0)&&strcmp(event_list[num_event].place, event_list[i].place) == 0) break;
                    }
                    if(i < num_event) error_flag = temp_error_flag = 6;
                }
                if(temp_error_flag == 0){
                    FILE *fp = fopen("text_files/events.txt", "a");
                    fputs(event_list[num_event].name, fp); fputs("\n", fp);
                    fputs(event_list[num_event].place, fp); fputs("\n", fp);
                    fputs(event_list[num_event].begin, fp); fputs("\n", fp);
                    fputs(event_list[num_event].end, fp); fputs("\n", fp);
                    fclose(fp);
                    page_ind = 4; num_event++; error_flag = 0;
                }
            }
            else input_ind = 0;
            printf("%d\n", num_event);
        }

    }

    if(page_ind == 41){
        
        //DELETE EVENT PAGE CONTROL
        
        if(button == GLUT_LEFT_BUTTON && state == GLUT_DOWN){
            if(position_check(mx, 100, 400)&&position_check(my, 400, 420)){
                input_ind = 1; 
            }
            else if(position_check(mx, 205, 277)&&position_check(my, 100, 130)){
                int i, temp_error_flag = 0;
                for(i = 0;i < num_event;i++){
                    if(strcmp(event_list[i].name, event_list[100].name) == 0) break; 
                }
                if(i == num_event) error_flag = temp_error_flag = 3;
                if(temp_error_flag == 0){
                    error_flag = 0;
                    event_list[i].name[0] = '\0';
                    FILE *fp = fopen("text_files/events.txt", "w");
                    for(i = 0;i < num_event;i++){
                        if(strlen(event_list[i].name) == 0) continue;
                        fputs(event_list[i].name, fp); fputs("\n", fp);
                        fputs(event_list[i].place, fp); fputs("\n", fp);
                        fputs(event_list[i].begin, fp); fputs("\n", fp);
                        fputs(event_list[i].end, fp); fputs("\n", fp);
                    }
                    fclose(fp); i = 0;
                    fp = fopen("text_files/events.txt", "r");
                    while(1){
                        if(fgets(event_list[i].name, 80, fp) == NULL) break;
                        fgets(event_list[i].place, 80, fp);
                        fgets(event_list[i].begin, 30, fp);
                        fgets(event_list[i].end, 30, fp);
                        event_list[i].place[strlen(event_list[i].place)-1] = '\0';
                        event_list[i].name[strlen(event_list[i].name)-1] = '\0';
                        event_list[i].begin[strlen(event_list[i].begin)-1] = '\0';
                        event_list[i].end[strlen(event_list[i].end)-1] = '\0';
                        i++;
                    }
                    fclose(fp);
                    event_list[100].name[0] = event_list[num_event-1].name[0] = '\0';
                    event_list[100].place[0] = event_list[num_event-1].place[0] = '\0';
                    event_list[100].begin[0] = event_list[num_event-1].begin[0] = '\0';
                    event_list[100].end[0] = event_list[num_event-1].end[0] = '\0';

                    num_event--; page_ind = 4, error_flag = 0;
                }
            }
            else input_ind = 0;
        }
    }

}

/*
function iKeyboard() is called whenever the user hits a key in keyboard.
key- holds the ASCII value of the key pressed.
*/

void iKeyboard(unsigned char key)
{
    
    //NEW STUDENT PAGE INPUT

    if(page_ind == 23){
        if(input_ind == 1){
            if(key == 8) {student_list[num_student].name[MAX(0,strlen(student_list[num_student].name)-1)] = '\0';}
            else{
                student_list[num_student].name[strlen(student_list[num_student].name)+1] = '\0';
                student_list[num_student].name[strlen(student_list[num_student].name)] = key;
            }
        }
        if(input_ind == 2){
            if(key == 8) {student_list[num_student].stuid[MAX(0,strlen(student_list[num_student].stuid)-1)] = '\0';}
            else{
                student_list[num_student].stuid[strlen(student_list[num_student].stuid)+1] = '\0';
                student_list[num_student].stuid[strlen(student_list[num_student].stuid)] = key;
            }
        }
        if(input_ind == 3){
            if(key == 8) {student_list[num_student].dept[MAX(0,strlen(student_list[num_student].dept)-1)] = '\0';}
            else{
                student_list[num_student].dept[strlen(student_list[num_student].dept)+1] = '\0';
                student_list[num_student].dept[strlen(student_list[num_student].dept)] = key;
            }
        }
        if(input_ind == 4){
            if(key == 8) {student_list[num_student].status[MAX(0,strlen(student_list[num_student].status)-1)] = '\0';}
            else{
                student_list[num_student].status[strlen(student_list[num_student].status)+1] = '\0';
                student_list[num_student].status[strlen(student_list[num_student].status)] = key;
            }
        }
        if(input_ind == 5){
            if(key == 8) {student_list[num_student].phone[MAX(0,strlen(student_list[num_student].phone)-1)] = '\0';}
            else{
                student_list[num_student].phone[strlen(student_list[num_student].phone)+1] = '\0';
                student_list[num_student].phone[strlen(student_list[num_student].phone)] = key;
            }
        }
    }

    if(page_ind == 25){

        // EDIT STUDENT INPUT CONTROL

        if(input_ind == 1){
            if(key == 8) {student_list[1000].name[MAX(0,strlen(student_list[1000].name)-1)] = '\0';}
            else{
                student_list[1000].name[strlen(student_list[1000].name)+1] = '\0';
                student_list[1000].name[strlen(student_list[1000].name)] = key;
            }
        }
        if(input_ind == 2){
            if(key == 8) {student_list[1000].stuid[MAX(0,strlen(student_list[1000].stuid)-1)] = '\0';}
            else{
                student_list[1000].stuid[strlen(student_list[1000].stuid)+1] = '\0';
                student_list[1000].stuid[strlen(student_list[1000].stuid)] = key;
            }
        }
        if(input_ind == 3){
            if(key == 8) {student_list[1000].dept[MAX(0,strlen(student_list[1000].dept)-1)] = '\0';}
            else{
                student_list[1000].dept[strlen(student_list[1000].dept)+1] = '\0';
                student_list[1000].dept[strlen(student_list[1000].dept)] = key;
            }
        }
        if(input_ind == 4){
            if(key == 8) {student_list[1000].status[MAX(0,strlen(student_list[1000].status)-1)] = '\0';}
            else{
                student_list[1000].status[strlen(student_list[1000].status)+1] = '\0';
                student_list[1000].status[strlen(student_list[1000].status)] = key;
            }
        }
        if(input_ind == 5){
            if(key == 8) {student_list[1000].phone[MAX(0,strlen(student_list[1000].phone)-1)] = '\0';}
            else{
                student_list[1000].phone[strlen(student_list[1000].phone)+1] = '\0';
                student_list[1000].phone[strlen(student_list[1000].phone)] = key;
            }
        }
        if(input_ind == 6){
            if(key == 8) {student_list[1001].stuid[MAX(0,strlen(student_list[1001].stuid)-1)] = '\0';}
            else{
                student_list[1001].stuid[strlen(student_list[1001].stuid)+1] = '\0';
                student_list[1001].stuid[strlen(student_list[1001].stuid)] = key;
            }
        }
    }

    if(page_ind == 24){
        
        //QUERY STUDENT INPUT CONTROL
        
        if(input_ind == 1){
            if(key == 8) {student_list[1000].name[MAX(0,strlen(student_list[1000].name)-1)] = '\0';}
            else{
                student_list[1000].name[strlen(student_list[1000].name)+1] = '\0';
                student_list[1000].name[strlen(student_list[1000].name)] = key;
            }
        }
        if(input_ind == 2){
            if(key == 8) {student_list[1000].stuid[MAX(0,strlen(student_list[1000].stuid)-1)] = '\0';}
            else{
                student_list[1000].stuid[strlen(student_list[1000].stuid)+1] = '\0';
                student_list[1000].stuid[strlen(student_list[1000].stuid)] = key;
            }
        }
        if(input_ind == 3){
            if(key == 8) {student_list[1000].dept[MAX(0,strlen(student_list[1000].dept)-1)] = '\0';}
            else{
                student_list[1000].dept[strlen(student_list[1000].dept)+1] = '\0';
                student_list[1000].dept[strlen(student_list[1000].dept)] = key;
            }
        }
        if(input_ind == 4){
            if(key == 8) {student_list[1000].status[MAX(0,strlen(student_list[1000].status)-1)] = '\0';}
            else{
                student_list[1000].status[strlen(student_list[1000].status)+1] = '\0';
                student_list[1000].status[strlen(student_list[1000].status)] = key;
            }
        }
        if(input_ind == 5){
            if(key == 8) {student_list[1000].phone[MAX(0,strlen(student_list[1000].phone)-1)] = '\0';}
            else{
                student_list[1000].phone[strlen(student_list[1000].phone)+1] = '\0';
                student_list[1000].phone[strlen(student_list[1000].phone)] = key;
            }
        }
    }

    //DELETE STUDENT PAGE INPUT

    if(page_ind == 21){
        if(input_ind == 1){
            if(key == 8) {student_list[1000].stuid[MAX(0,strlen(student_list[1000].stuid)-1)] = '\0';}
            else{
                student_list[1000].stuid[strlen(student_list[1000].stuid)+1] = '\0';
                student_list[1000].stuid[strlen(student_list[1000].stuid)] = key;
            }
        }
    }

    //MAKE PAYMENT PAGE INPUT

    if(page_ind == 31){
        if(input_ind == 1){
            if(key == 8) {stid[MAX(0,strlen(stid)-1)] = '\0';}
            else{
                stid[strlen(stid)+1] = '\0';
                stid[strlen(stid)] = key;
            }
        }
        if(input_ind == 2){
            if(key == 8) {mnth[MAX(0,strlen(mnth)-1)] = '\0';}
            else{
                mnth[strlen(mnth)+1] = '\0';
                mnth[strlen(mnth)] = key;
            }
        }
    }

    if(page_ind == 43){
        
        //NEW EVENT PAFE INPUT
        
        if(input_ind == 1){
            if(key == 8) {event_list[num_event].name[MAX(0,strlen(event_list[num_event].name)-1)] = '\0';}
            else{
                event_list[num_event].name[strlen(event_list[num_event].name)+1] = '\0';
                event_list[num_event].name[strlen(event_list[num_event].name)] = key;
            }
        }
        if(input_ind == 2){
            if(key == 8) {event_list[num_event].place[MAX(0,strlen(event_list[num_event].place)-1)] = '\0';}
            else{
                event_list[num_event].place[strlen(event_list[num_event].place)+1] = '\0';
                event_list[num_event].place[strlen(event_list[num_event].place)] = key;
            }
        }
        if(input_ind == 3){
            if(key == 8) {event_list[num_event].begin[MAX(0,strlen(event_list[num_event].begin)-1)] = '\0';}
            else{
                event_list[num_event].begin[strlen(event_list[num_event].begin)+1] = '\0';
                event_list[num_event].begin[strlen(event_list[num_event].begin)] = key;
            }
        }
        if(input_ind == 4){
            if(key == 8) {event_list[num_event].end[MAX(0,strlen(event_list[num_event].end)-1)] = '\0';}
            else{
                event_list[num_event].end[strlen(event_list[num_event].end)+1] = '\0';
                event_list[num_event].end[strlen(event_list[num_event].end)] = key;
            }
        }
    }

    if(page_ind == 41){
        
        //DELETE EVENT PAGE INPUT
        
        if(input_ind == 1){
            if(key == 8) {event_list[100].name[MAX(0,strlen(event_list[100].name)-1)] = '\0';}
            else{
                event_list[100].name[strlen(event_list[100].name)+1] = '\0';
                event_list[100].name[strlen(event_list[100].name)] = key;
            }
        }

    }
}

/*
function iSpecialKeyboard() is called whenver user hits special keys likefunction keys, home, end, pg up, pg down, arraows etc. you have to use
appropriate constants to detect them. A list is:
GLUT_KEY_F1, GLUT_KEY_F2, GLUT_KEY_F3, GLUT_KEY_F4, GLUT_KEY_F5, GLUT_KEY_F6,
GLUT_KEY_F7, GLUT_KEY_F8, GLUT_KEY_F9, GLUT_KEY_F10, GLUT_KEY_F11,
GLUT_KEY_F12, GLUT_KEY_LEFT, GLUT_KEY_UP, GLUT_KEY_RIGHT, GLUT_KEY_DOWN,
GLUT_KEY_PAGE UP, GLUT_KEY_PAGE DOWN, GLUT_KEY_HOME, GLUT_KEY_END,
GLUT_KEY_INSERT */

void iSpecialKeyboard(unsigned char key)
{
    
    //LIST NAVIGATION CONTROL

    if(key == GLUT_KEY_UP){
        if(page_ind == 5) decreament();
        if(page_ind == 42) decreament();
        if(page_ind == 22) decreament();
        if(page_ind == 241) decreament();
        if(page_ind == 32) decreament();
    }
    if(key == GLUT_KEY_DOWN){
        if(page_ind == 5) increament(6);
        if(page_ind == 42) increament(MAX(0, num_event-6));
        if(page_ind == 241) increament(MAX(0, num_stuquery-6));
        if(page_ind == 22) increament(MAX(0, num_student-6));
        if(page_ind == 32) increament(MAX(0, num_defaulter-6));
    }

    //EXIT CONTROL

    if(key == GLUT_KEY_END) exit(0);

}

int main()
{

//place your own initialization codes here.
//INITIALIZING DATA STRUCTURE

for(int i = 0;i < 102;i++){
    event_list[i].name[0] = '\0'; event_list[i].place[0] = '\0';
    event_list[i].begin[0] = '\0'; event_list[i].end[0] = '\0';
}

for(int i = 0;i < 1002;i++){
    student_list[i].name[0] = '\0'; student_list[i].status[0] = '\0';
    student_list[i].dept[0] = '\0'; student_list[i].stuid[0] = '\0';
    student_list[i].phone[0] = '\0';
}

for(int i = 0;i < 1002;i++){
    de_list[i].stu_id[0] = '\0';
}

mnth[0] = stid[0] = '\0';

//RETREIVING DATA FROM THE TEXT FILES

FILE *fp = fopen("text_files/students.txt", "r");
while(1){
    if(fgets(student_list[num_student].name, 80, fp) == NULL) break;
    fgets(student_list[num_student].stuid, 20, fp);
    fgets(student_list[num_student].dept, 20, fp);
    fgets(student_list[num_student].status, 20, fp);
    fgets(student_list[num_student].phone, 20, fp);
    char temp[20];
    fgets(temp, 20, fp);
    student_list[num_student].name[strlen(student_list[num_student].name)-1] = '\0';
    student_list[num_student].stuid[strlen(student_list[num_student].stuid)-1] = '\0';
    student_list[num_student].dept[strlen(student_list[num_student].dept)-1] = '\0';
    student_list[num_student].status[strlen(student_list[num_student].status)-1] = '\0';
    student_list[num_student].phone[strlen(student_list[num_student].phone)-1] = '\0';
    temp[strlen(temp)-1] = '\0';
    student_list[num_student].pay_mon = atoi(temp);
    num_student++;
}
fclose(fp);

fp = fopen("text_files/events.txt", "r");
while(1){
    if(fgets(event_list[num_event].name, 80, fp) == NULL) break;
    fgets(event_list[num_event].place, 80, fp);
    fgets(event_list[num_event].begin, 30, fp);
    fgets(event_list[num_event].end, 30, fp);
    event_list[num_event].place[strlen(event_list[num_event].place)-1] = '\0';
    event_list[num_event].name[strlen(event_list[num_event].name)-1] = '\0';
    event_list[num_event].begin[strlen(event_list[num_event].begin)-1] = '\0';
    event_list[num_event].end[strlen(event_list[num_event].end)-1] = '\0';
    num_event++;
}
fclose(fp);

iInitialize(512, 512, "management");
return 0;
}