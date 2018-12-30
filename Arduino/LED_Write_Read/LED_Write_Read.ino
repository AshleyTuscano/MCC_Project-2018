char command;
String string;
#define motor1Negative 4
#define motor1Positive 5
#define motor2Negative 6
#define motor2Positive 7

  void setup()
  {
    Serial.begin(9600);
    pinMode(motor1Negative, OUTPUT);
    pinMode(motor1Positive, OUTPUT);
    pinMode(motor2Negative, OUTPUT);
    pinMode(motor2Positive, OUTPUT);
  }

  void loop()
  {
    if (Serial.available() > 0) 
    {string = "";}
    
    while(Serial.available() > 0)
    {
      command = ((byte)Serial.read());
      
      if(command == ':')
      {
        break;
      }
      
      else
      {
        string += command;
      }
      
      delay(1);
    }
    
    if(string == "LF")
    {
        leftMotorForward();
    }
    
    if(string =="LO")
    {
        leftMotorOff();
    }
    if(string == "LB")
    {
        leftMotorBackward();
    }
    if(string == "RF")
    {
        rightMotorForward();
    }
    if(string == "RO")
    {
        rightMotorOff();
    }
    if(string == "RB")
    {
        rightMotorBackward();
    }
 }
 
void leftMotorForward()
   {
      analogWrite(motor1Negative, 0);
      analogWrite(motor1Positive, 255);
      delay(10);
    }
void leftMotorOff()
   {
      analogWrite(motor1Negative, 0);
      analogWrite(motor1Positive, 0);
      delay(10);
    }
void leftMotorBackward()
   {
      analogWrite(motor1Negative, 255);
      analogWrite(motor1Positive, 0);
      delay(10);
    }
void rightMotorForward()
   {
      analogWrite(motor2Negative, 0);
      analogWrite(motor2Positive, 255);
      delay(10);
    }
void rightMotorOff()
   {
      analogWrite(motor2Negative, 0);
      analogWrite(motor2Positive, 0);
      delay(10);
    }    
void rightMotorBackward()
   {
      analogWrite(motor2Negative, 255);
      analogWrite(motor2Positive, 0);
      delay(10);
    }




    
