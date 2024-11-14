package ru.vladshi.javalearning.tennisscoreboard.util;

import java.util.LinkedList;
import java.util.List;

public class PaginationUtil {

    public static List<Integer> getPageNumberRange(int currentPage, int lastPageNumber, int maxQuantity) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(currentPage);
        for (int i = 1; i <= maxQuantity; i++) {
            int previousPage = currentPage - i;
            if (previousPage > 0)
                list.addFirst(previousPage);
            if (list.size() >= maxQuantity) {
                break;
            }
            int nextPage = currentPage + i;
            if (nextPage <= lastPageNumber)
                list.addLast(nextPage);
            if (list.size() >= maxQuantity) {
                break;
            }
        }
        return list;
    }
}
