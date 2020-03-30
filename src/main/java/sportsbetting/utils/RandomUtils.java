package sportsbetting.utils;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomUtils {
    public int defineRandomNumber(int size){
        return new Random().nextInt(size);
    }
}
