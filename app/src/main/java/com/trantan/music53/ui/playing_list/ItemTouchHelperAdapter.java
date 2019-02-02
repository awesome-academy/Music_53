package com.trantan.music53.ui.playing_list;

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemMoved();
}
