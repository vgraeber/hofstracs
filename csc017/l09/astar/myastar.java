import java.util.Optional;

public class myastar extends astar_base {
  public myastar(int r, int c) {
    super(r, c);
  }
  @Override
  public void customize() {
    ////// Things you can do here...
    //setcosts(2, 0, 1, 10); // cost of land, desert, fire, water
    //pathfinder.gap = 15; // change size of graphical hexgagons
    //pathfinder.yoff = 20; // graphical top margin adjustment
    //pathfinder.delaytime = 300; //change animation speed
    //setRandFactor(0.13); // increase amount of water/fire
  }
  public static void main(String[] av) {
    pathfinder.main(av);
  }
  int getHeapKey(coord newCoord) {
    return ((newCoord.y * COLS) + newCoord.x);
  }
  boolean inBounds(int row, int col) {
    if ((0 <= row) && (row < ROWS) && (0 <= col) && (col < COLS)) {
      return true;
    } else {
      return false;
    }
  }
  @Override
  public Optional<coord> search(int sourceRow, int sourceCol, int destRow, int destCol)  {
    HashedHeap.set_initial_capacity(ROWS * COLS);
    HashedHeap frontier = new HashedHeap<Integer, coord>((x, y) -> y.compareTo(x));
    boolean[][] interior = new boolean[ROWS][COLS];
    coord currCoord = new coord(sourceRow, sourceCol);
    currCoord.add_estimated_cost(hexdist(sourceRow, sourceCol, destRow, destCol));
    frontier.set(getHeapKey(currCoord), currCoord);
    while (frontier.size() > 0) {
      Optional<KVPair<Integer, coord>> topCoordPair = frontier.pop();
      currCoord = topCoordPair.get().val();
      interior[currCoord.y][currCoord.x] = true;
      if (interior[destRow][destCol]) {
        return Optional.of(currCoord);
      }
      for (int i = 0; i < 6; i++) {
        int nextY = currCoord.y + DY[i];
        int nextX = currCoord.x + DX[currCoord.y % 2][i];
        if (inBounds(nextY, nextX) && (costof[Map[nextY][nextX]] >= 0) && !interior[nextY][nextX]) {
          coord nextCoord = make_neighbor(currCoord, nextY, nextX, destRow, destCol);
          Optional<coord> replacedCoord = frontier.get(getHeapKey(nextCoord));
          if (!replacedCoord.isPresent() || (nextCoord.compareTo(replacedCoord.get()) < 0)) {
            frontier.set(getHeapKey(nextCoord), nextCoord);
          }
        }
      }
    }
    return Optional.empty();
  }
}