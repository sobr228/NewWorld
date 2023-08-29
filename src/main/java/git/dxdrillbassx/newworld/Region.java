package git.dxdrillbassx.newworld;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Region {

    private static final List<Region> regionList = new ArrayList<>();
    public static Material wandItem = Material.WOODEN_SHOVEL; // Да Да иди нахуй класс wand с shovel

    public static Region getRegionOfAPlayer(Player player){ // Получаем регион игрока по нику..
        for (Region region: regionList){ // Интеграция в списки регионов..
            if (region.owner == player)
                return region;

        }

        return null;
    }

    private Location pos1, pos2;

    private Player owner; // Уже какой-то WorldGuard а не WorldEdit...

    public Region(Player owner) {
        this.owner = owner;

        regionList.add(this);
    }

    private boolean checkPos(){
        if (pos1.getWorld() != pos2.getWorld()) { // ПроверОчка на мир
            owner.sendMessage(Signature.ERROR + "Точки находятся в разных мирах!");
            return false;
        }

        return true;
    }

    public void setBlock(Material material){
        if (!checkPos()) // ПроверОчка на мир
            return;

        // Заменяем все блоки во всем регионе
        int x1 = pos1.getBlockX();
        int y1 = pos1.getBlockY();
        int z1 = pos1.getBlockZ();

        int x2 = pos2.getBlockX();
        int y2 = pos2.getBlockY();
        int z2 = pos2.getBlockZ();

        Location location;

        // Перестановка значений местами если вдруг одно из них больше..
        if (x1 > x2){
            int t = x1;
            x1 = x2;
            x2 = t;
        }

        if (y1 > y2){
            int t = y1;
            y1 = y2;
            y2 = t;
        }

        if (z1 > z2){
            int t = z1;
            z1 = z2;
            z2 = t;
        }

        for (int x = x1; x <= x2; x++){ //Вложенный цыкл 1..5
            for (int y = y1; y <= y2; y++){ //Вложенный цыкл 1..5
                for (int z = z1; z <= z2; z++){ //Вложенный цыкл 1..5
                    location = new Location(pos1.getWorld(), x, y, z);
                    location.getBlock().setType(material);
                }
            }
        }

        owner.sendMessage(Signature.MAIN + "Успешно!"); // Означет что все заебись
    }

    // Логика работы для нашего BlockFace
    public void expand(int blockNum, BlockFace side){
        if (!checkPos()) // ПроверОчка на мир
            return;

        if (side == BlockFace.NORTH){
            if (pos1.getZ() < pos2.getZ()){
                pos1.setZ(pos1.getZ() - blockNum);
            }
            else {
                pos2.setZ(pos2.getZ() - blockNum);
            }
        }
        else if (side == BlockFace.EAST){
            if (pos1.getX() > pos2.getX()){
                pos1.setX(pos1.getZ() + blockNum);
            }
            else {
                pos2.setX(pos2.getZ() + blockNum);
            }
        }
        else if (side == BlockFace.WEST){
            if (pos1.getX() < pos2.getX()){
                pos1.setX(pos1.getZ() - blockNum);
            }
            else {
                pos2.setX(pos2.getZ() - blockNum);
            }
        }
        else if (side == BlockFace.SOUTH){
            if (pos1.getZ() > pos2.getZ()){
                pos1.setZ(pos1.getZ() + blockNum);
            }
            else {
                pos2.setZ(pos2.getZ() + blockNum);
            }
        }
    }

    public void replace(Material targetBlock, Material toBlock){ // Получение позиции блоков | Заменяймый блок | На что заменяется
        if (!checkPos()) // ПроверОчка на мир
            return;

        int startX = Math.min(pos1.getBlockX(), pos2.getBlockX()), startY = Math.min(pos1.getBlockY(), pos2.getBlockY()),
                startZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        int endX = Math.max(pos1.getBlockX(), pos2.getBlockX()), endY = Math.max(pos1.getBlockY(), pos2.getBlockY()),
                endZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());

        Location location;
        for (int x = startX; x <= endX; x++){ // Очередной цикл..x
            for (int y = startY; y <= endY; y++){ // Очередной цикл..y
                for (int z = startZ; z <= endZ; z++){ // Очередной цикл..z
                    location = new Location(pos1.getWorld(), x, y, z);
                    if (location.getBlock().getType() == targetBlock) {
                        location.getBlock().setType(toBlock);
//                       BlockFace blockFace = location.getBlock().getFace(location.getBlock());  Сохранение направления смотрения
                    }
                }
            }
        }
    }

    // Анимация выделения региона
    public void showRegion(){

    }

    // Отмена действия
    public void undo(){
        //TODO: временно не ебу как реализовать
    }

    // Копирование выделеного региона
    public void copy(){
        //TODO: временно не ебу как реализовать
    }

    // Вставка скопированого региона
    public void paste(){
        //TODO: временно не ебу как реализовать
    }

    // GETTER & SETTER //

    public Location getPos1(){
        return pos1;
    }

    public Location getPos2(){
        return pos2;
    }

    public Player getOwner() {
        return owner;
    }

    public void setPos1(Location pos1){
        this.pos1 = pos1;
    }

    public void setPos2(Location pos2){
        this.pos2 = pos2;
    }
}
