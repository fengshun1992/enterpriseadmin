package com.admin.filter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class myFilterFilter implements FilenameFilter {
    List<String> list = new ArrayList<>();
    private boolean has;
    public myFilterFilter(boolean has, String... suffix) {
        this.has=has;
        for (String s : suffix) {
            list.add(s);
        }
    }
    @Override
    public boolean accept(File dir, String name) {
        for (String suffix : list) {
            if (name.endsWith(suffix)) {
                return has;
            }
        }
        return !has;
    }
}
