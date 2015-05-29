/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.doshisha.bil0167.tool.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author katsuya
 */
public class BasicTextProcessing {

    private static String newkey;
    private static ArrayList selectedtag;

    public static void setSelectedtag(ArrayList selectedtag) {
        BasicTextProcessing.selectedtag = selectedtag;
    }

    /**
     * @param args the command line arguments
     */
    public static ArrayList CreateMaps(int num) {
        ArrayList array = new ArrayList();
        for (int i = 0; i < num; i++) {
            HashMap<String, HashMap> hash = new HashMap<String, HashMap>();
            array.add(hash);
        }
        return array;

    }

    public static void registerKey(HashMap<String, HashMap> parenthash, String key, HashMap<String, HashMap> childhash) {
        parenthash.put(key, childhash);
    }

    public static ArrayList NewRegisterKeys(ArrayList<String> keys, int value, ArrayList array) {
                //  ArrayList array = CreateMaps(keys.size()-1);
        //   HashMap<String,Integer> lasthash = new HashMap<String,Integer>();
        int index = -2;
        try {
            //        System.out.println("index:\t"+isSerchKeys(keys, (Map)array.get(0)));
            index = isSerchKeys(keys, (Map) array.get(0));
        } catch (Exception ex) {

        }
        if (index >= 0) {
            for (int i = index; i < keys.size(); i++) {
                if (i == keys.size() - 1) {
                    HashMap hash = (HashMap) array.get(i);

                    hash.put(keys.get(i), value);
                    //     System.out.println(hash);
                    break;
                } else {
                    registerKey((HashMap) array.get(i), keys.get(i), (HashMap) array.get(i + 1));
                }
            }
            return array;

        }

        for (int i = 0; i < keys.size(); i++) {
            if (i == keys.size() - 1) {
                HashMap hash = (HashMap) array.get(i);

                hash.put(keys.get(i), value);
                //     System.out.println(hash);
                break;
            } else {
                registerKey((HashMap) array.get(i), keys.get(i), (HashMap) array.get(i + 1));
            }
        }
                //  lasthash.put(keys.get(keys.size()-1), value);
        //   array.add(lasthash);  

        return array;
    }

    public static Map InsertRegisterKeys(ArrayList<String> keys, int value, Map map) {
                //  ArrayList array = CreateMaps(keys.size()-1);
        //   HashMap<String,Integer> lasthash = new HashMap<String,Integer>();
        if (map.isEmpty()) {
            ArrayList CreateMaps = CreateMaps(keys.size());
            ArrayList NewRegisterKeys = NewRegisterKeys(keys, value, CreateMaps);
            //   System.out.println(NewRegisterKeys.get(0));
            return (Map) NewRegisterKeys.get(0);
        }
        int index = -2;
        try {
            //  System.out.println("index:\t"+isSerchKeys(keys,map));
            index = isSerchKeys(keys, map);
        } catch (Exception ex) {

        }
        Map tempmap = map;
        if (index == -1) {
            ArrayList CreateMaps = CreateMaps(keys.size());
            ArrayList NewRegisterKeys = NewRegisterKeys(keys, value, CreateMaps);
            //   System.out.println(NewRegisterKeys.get(0));
            return (Map) NewRegisterKeys.get(0);
        }
        if (index >= 0) {
            ArrayList newmap = CreateMaps(keys.size() - index - 1);
            ArrayList newkeys = new ArrayList();
            try {
                newkey = keys.get(index + 1);
            } catch (Exception ex) {
                newkey = keys.get(index);
            }

            //     System.out.println(keys.get(index));
            for (int i = index + 2; i < keys.size(); i++) {
                newkeys.add(keys.get(i));
            }

            ArrayList newlist = NewRegisterKeys(newkeys, value, newmap);
            Map newrootmap = (Map) newlist.get(0);

            for (int i = 0; i <= index; i++) {
                Map temp = getChildMap(keys.get(i), tempmap);
                tempmap = temp;
            }
//                    System.out.println("tempmap\t"+tempmap);
//                    System.out.println(keys.get(index));
//                    System.out.println(newrootmap);
            if (newrootmap.isEmpty()) {
                tempmap.put(newkey, value);
                return map;
            }
            tempmap.put(newkey, newrootmap);

        }

        return map;
    }

    public static String getValue(ArrayList<String> keys, ArrayList dataarray) {
        String result = null;

        Map parentMap = (HashMap) dataarray.get(0);
        for (int i = 0; i <= keys.size() - 1; i++) {
            if (i == keys.size() - 1) {

                result = parentMap.get(keys.get(keys.size() - 1)).toString();

            } else {
                Map childMap = getChildMap(keys.get(i), (HashMap) parentMap);
                parentMap = childMap;
            }
        }
        return result;
    }

    public static String getValue(ArrayList<String> keys, Map map) {
        String result = "-1";

        Map parentMap = map;
        for (int i = 0; i <= keys.size() - 1; i++) {
            if (i == keys.size() - 1) {
                try {
                    result = parentMap.get(keys.get(keys.size() - 1)).toString();
                } catch (Exception ex) {

                }

            } else {
                Map childMap = getChildMap(keys.get(i), (HashMap) parentMap);
                parentMap = childMap;
            }
        }
        return result;
    }

    public static Map getChildMap(String key, Map hash) {
        Map childMap = (Map) hash.get(key);

        return childMap;
    }

    public static int isSerchKeys(ArrayList<String> keys, Map resisteredMap) {
        int index = -1;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if (isSerchKey(key, resisteredMap)) {
                try {
                    Map childMap = (Map) resisteredMap.get(key);
                    resisteredMap = childMap;
                } catch (Exception ex) {
                    int value = (int) resisteredMap.get(key);

                    index = i;
                    break;
                }

                index = i;
            } else {
                break;
            }

        }
        return index;
    }

    public static boolean isSerchKey(String key, Map<String, String> resisteredMap) {
        //   System.out.println(resisteredMap);
        Object[] mapKeyset = resisteredMap.keySet().toArray();

        //  System.out.println(key+"\t"+mapKeyset[0]);
        for (int i = 0; i < mapKeyset.length; i++) {
            if (key.equals(mapKeyset[i])) {

                return true;
            } else {

            }
        }
        return false;
    }

}
