package net.moltendorf.Bukkit.IntelliDoors.controller

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace

/**
 * Created by moltendorf on 15/05/23.

 * @author moltendorf
 */
class SingleDoor(protected var top: Block, protected var bottom: Block) : AbstractDoor() {
    private val type: Material

    private val location: Location

    internal var topData: Byte = 0
    internal var bottomData: Byte = 0

    init {
        type = top.type
        location = top.location

        topData = top.data
        bottomData = bottom.data
    }

    val facing: BlockFace
        get() = Facing[bottomData.toInt() and 3]

    val isLeft: Boolean
        get() = topData.toInt() and 1 == 0

    val isRight: Boolean
        get() = topData.toInt() and 1 == 1

    override fun isClosed(): Boolean {
        return bottomData.toInt() and 4 == 0
    }

    override fun isOpened(): Boolean {
        return bottomData.toInt() and 4 == 4
    }

    override fun toggle() {
        bottomData = (bottomData + (if (isClosed) 4 else -4)).toByte()
        bottom.data = bottomData
    }

    override fun close() {
        if (isOpened) {
            bottomData = (bottomData - 4).toByte()

            bottom.data = bottomData
        }
    }

    override fun open() {
        if (isClosed) {
            bottomData = (bottomData + 4).toByte()

            bottom.data = bottomData
        }
    }

    override fun overrideState(closed: Boolean) {
        if (isClosed != closed) {
            bottomData = (bottomData - (if (closed) 4 else -4)).toByte()
        }
    }

    override fun getLocation(): Location {
        return location
    }

    override fun getType(): Material {
        return type
    }

    companion object {
        operator fun get(block: Block): SingleDoor? {
            // org.bukkit.material.SingleDoor: Deprecated? Is this really so hard? Sigh.
            val data = block.data

            val top: Block
            val bottom: Block

            if (data.toInt() and 8 == 8) {
                // Top of door.
                top = block
                bottom = block.getRelative(BlockFace.DOWN)
            } else {
                // Bottom of door.
                top = block.getRelative(BlockFace.UP)
                bottom = block
            }

            if (top.type == bottom.type) {
                return SingleDoor(top, bottom)
            }

            return null
        }

        private val Facing = arrayOf(BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH, BlockFace.EAST)
    }
}
