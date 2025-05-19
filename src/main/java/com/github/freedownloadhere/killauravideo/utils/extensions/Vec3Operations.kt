package com.github.freedownloadhere.killauravideo.utils.extensions

import net.minecraft.util.Vec3

operator fun Vec3.plus(other: Vec3): Vec3 {
    return Vec3(xCoord + other.xCoord, yCoord + other.yCoord, zCoord + other.zCoord)
}

operator fun Vec3.minus(other: Vec3): Vec3 {
    return Vec3(xCoord - other.xCoord, yCoord - other.yCoord, zCoord - other.zCoord)
}

operator fun Vec3.times(scalar: Double): Vec3 {
    return Vec3(xCoord * scalar, yCoord * scalar, zCoord * scalar)
}
