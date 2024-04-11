package com.example.examplemod.capability.FarmXp;

import net.minecraft.nbt.CompoundTag;

public class PlayerFarmXp {
    private int xp;

    public PlayerFarmXp() {
        this.xp = 0;
    }

    public int getXp() {
        return xp;
    }

    public void setXp() {
        this.xp = xp;
    }

    public void increase() {
        xp++;
    }

    public void increase(int i) {
        xp += i;
    }


    public boolean decrease(int d) {
        if (xp >= d) {
            xp -= d;
            return true;
        } else {
            return false;
        }
    }
    public boolean decrease(){
        return this.decrease(1);
    }
    public void saveNBTdata(CompoundTag compoundTag) {
        compoundTag.putInt("farmxp", xp);
    }

    public void loadNBTdata(CompoundTag compoundTag) {
        xp = compoundTag.getInt("farmxp");
    }
}
