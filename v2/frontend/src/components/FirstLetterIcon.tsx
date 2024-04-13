import React from 'react'
import { IconType } from 'react-icons';
import { FaA, FaB, FaC, FaD, FaE, FaF, FaG, FaH, FaI, FaJ, FaK, FaL, FaM, FaN, FaO, FaP, FaQ, FaR, FaS, FaT, FaU, FaUser, FaV, FaW, FaX, FaY, FaZ } from 'react-icons/fa6';

const iconMap: { [key: string]: IconType } = {
    'A': FaA,
    'B': FaB,
    'C': FaC,
    'D': FaD,
    'E': FaE,
    'F': FaF,
    'G': FaG,
    'H': FaH,
    'I': FaI,
    'J': FaJ,
    'K': FaK,
    'L': FaL,
    'M': FaM,
    'N': FaN,
    'O': FaO,
    'P': FaP,
    'Q': FaQ,
    'R': FaR,
    'S': FaS,
    'T': FaT,
    'U': FaU,
    'V': FaV,
    'W': FaW,
    'X': FaX,
    'Y': FaY,
    'Z': FaZ,
};

const getIconByFirstLetter = (letter: string) => {
    const upperCaseLetter = letter.toUpperCase();
    return iconMap[upperCaseLetter] || FaUser;
};

interface Props {
    letter: string
    className: string
}

export default function FirstLetterIcon({ letter, className }: Props) {

    const Icon = getIconByFirstLetter(letter)

    return (
        <Icon className={className} />
    )
}
