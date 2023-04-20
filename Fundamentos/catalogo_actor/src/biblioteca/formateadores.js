import React, { Component } from 'react'

export function titleCase(str) {
    return str?.toLowerCase()
    .split(' ')
    .map(word => word.charAt(0).toUpperCase() + word.slice(1))
    .join(' ');
}

