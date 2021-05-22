package com.wolfman.travel;

import com.wolfman.travel.Component.UuidComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;


@SpringBootTest
class SpringbootTravelApplicationTests {

    @Autowired
    UuidComponent myUuidUtil;

    @Test
    void test01()
    {
        LinkedList<Integer> ints = new LinkedList<Integer>();
        ints.add(1);
        ints.add(2);
        ints.add(3);
        ints.add(4);
        ints.add(5);
        ints.add(6);

        for (Integer anInt : ints) {
            System.out.println(anInt);
        }

        System.out.println("-------------------");

        
        for (Integer anInt : ints) {
            if(anInt==3)
            {
                ints.remove(anInt);
                break;
            }
        }

        for (Integer anInt : ints) {
            System.out.println(anInt);
        }
    }

}
