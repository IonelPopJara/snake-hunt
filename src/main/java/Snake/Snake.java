package Snake;



public class Snake {
  static final int GAME_FIELD_WIDTH = 20;
  static final int GAME_FIELD_HEIGHT = 15;
  static final int GAME_FIELD_UNITS = (GAME_FIELD_WIDTH * GAME_FIELD_HEIGHT);
  final int x[] = new int[GAME_FIELD_UNITS];
  final int y[] = new int[GAME_FIELD_UNITS];
  char direction = 'D';
  int bodyLength = 3;

  public Snake(){

  }
  public int getHead(){

  }

  public int getBody(){

  }
  public int getBodyLength(){
    return bodyLength;
  }

  public void move(){
    for(int i = bodyLength;i>0;i--){
      y[i] = y[i-1];
      x[i] = y[i-1];
    }
    switch (direction){

      case 'L':
        if(direction != 'R') {
          x[0] = x[0] - GAME_FIELD_UNITS;
          break;
        }
      case 'R':
        if(direction != 'L') {
          x[0] = x[0] + GAME_FIELD_UNITS;
          break;
        }
      case 'D':
        if(direction != 'U') {
          y[0] = y[0] + GAME_FIELD_UNITS;
          break;
        }
      case 'U':
        if(direction != 'D'){
        y[0] = y[0] - GAME_FIELD_UNITS;
    }
  }
}
}


