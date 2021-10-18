package fpinjava.state;

import fpinjava.common.Tuple;


public interface RNG {

  Tuple<Integer, RNG> nextInt();
}
