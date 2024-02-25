package com.iohao.game.common.kit.hutool;

/**
 * @author 渔民小镇
 * @date 2023-01-19
 */
interface HuFinder {

    int INDEX_NOT_FOUND = -1;

    /**
     * 返回开始位置，即起始字符位置（包含），未找到返回-1
     *
     * @param from 查找的开始位置（包含）
     * @return 起始字符位置，未找到返回-1
     */
    int start(int from);

    /**
     * 返回结束位置，即最后一个字符后的位置（不包含）
     *
     * @param start 找到的起始位置
     * @return 结束位置，未找到返回-1
     */
    int end(int start);

    /**
     * 复位查找器，用于重用对象
     *
     * @return this
     */
    default HuFinder reset() {
        return this;
    }
}
