package streams;


import java.util.stream.*;

import java.util.*;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        System.out.println(" - - TASK #1 - -");
        List<String> names = Arrays.asList("Alex","Peter","Maria","Steven","Anna","Violetta");
        System.out.println(fromListToString(names));

        System.out.println("\n - - TASK #2 - -");
        System.out.println(toUppAndSort(names));

        System.out.println("\n - - TASK #3 - -");
        String[] myArr = {"1, 2, 0", "4, 5"};
        System.out.println(toSortIntArray(myArr));

        System.out.println("\n - - TASK #4 - -");
        long a = 25214903917L;
        long c = 11L;
        long m = pow(2, 48);
        long seed = System.currentTimeMillis();
        Stream<Long> random = myRandom(a, c, m, seed);
        random.limit(10).forEach(System.out::println);

        System.out.println("\n - - TASK #5 - -");
        Stream<Integer> stream1 = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8});
        Stream<Integer> stream2 = Arrays.stream(new Integer[]{9,10,11,12,13});
        Stream<Integer> newStream = zip(stream1, stream2);
        System.out.println(newStream);
        //newStream.forEach(System.out::println);
        long count = newStream.count();
        System.out.println(count);
    }

    // task 5
    public static <T> Stream<T> zip(Stream<T> first, Stream<T> second) {
//        final Iterator<T> iteratorFirst = first.iterator();
//        final Iterator<T> iteratorSecond = second.iterator();
//
//         Iterator<T> iteratorNew = new Iterator<T>() {
//            @Override
//            public boolean hasNext() {
//                return iteratorFirst.hasNext() && iteratorSecond.hasNext();
//            }
//            @Override
//            public T next() {
//                return iteratorFirst.next();
//            }
//        };

//        Stream<T> tStream = first ;
//        return tStream
//                .limit(Math.min(first.count(), second.count()))
//                .flatMap((p) -> List.of(iteratorFirst.next(), iteratorSecond.next()).stream())
//                ;

        //Stream<Iterator<T>> iteratorNew1 = Stream.of(iteratorNew);
        long min = Math.min(first.count(), second.count());
        Stream<T> streamStream = (Stream<T>) Stream.of(first.limit(min).toArray(), second.limit(min).toArray())
                .flatMap(e -> (Stream<T>) Arrays.stream(e));
        Stream<T> stream = Stream
                .concat(first, second)
                //.limit(Math.min(first.count(), second.count()))
                ;
        return streamStream;
    }

    // add to task 4
    public static long pow(int value, int powValue) {
        if (powValue == 1) {
            return (long) value;
        } else {
            return (long) (value * pow(value, powValue - 1));
        }
    }

    // task 4
    private static Stream<Long> myRandom(long a, long c, long m, long seed){
        Stream<Long> iterate = Stream.iterate(seed, x -> (a * x + c) % m);
        return iterate;
    }

    // task 3
    private static String toSortIntArray(String[] myArr) {
        int[] ints = Arrays.stream(myArr)
                .map(arr -> arr.split(","))
                .flatMapToInt(arr -> Arrays.stream(arr).mapToInt(i -> Integer.parseInt(i.strip())))
                .sorted()
                .toArray();
        return Arrays.toString(ints);
    }

    // task 2
    private static List<String> toUppAndSort(List<String> names) {
        return names.stream()
                .map(name -> name.toUpperCase())
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    // task 1
    private static String fromListToString(List<String> names){
                return names.stream()
                        .filter(name -> names.indexOf(name)%2!=0)
                        .map(name -> " "+names.indexOf(name)+". " + name+", ")
                        .collect(Collectors.joining());
    }

}
