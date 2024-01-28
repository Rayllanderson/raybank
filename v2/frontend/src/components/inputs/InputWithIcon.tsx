import React, { useCallback, useRef, useState } from 'react'

interface InputWithIconsProps extends React.InputHTMLAttributes<HTMLInputElement> {
    icon: any;
}

export default function InputWithIcon({icon: Icon, ...props }: InputWithIconsProps) {

    const inputRef = useRef<HTMLInputElement>(null);
    const [isFocused, setIsFocused] = useState(false)
    const [isFilled, setIsFilled] = useState(false)

    const handleInputBlur = useCallback(() => {
        setIsFocused(false)
        const hasValue = !!inputRef.current?.value;
        setIsFilled(hasValue)
    }, []);

    return (
        <div className="relative rounded-md shadow-sm ">
            <div className="pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3">
                <span className="sm:text-sm">
                    <Icon className={`${(isFilled || isFocused) ? 'text-primary-2' : 'text-gray-400'} w-4 h-4 md:w-5 md:h5 lg:w-6 lg:h-6 `} />
                </span>
            </div>
            <input
                {...props}
                ref={inputRef}
                onBlur={handleInputBlur}
                onFocus={() => setIsFocused(true)}
                className="rounded-md w-full h-10 md:h-12 lg:h-14 border-0 pl-8 md:pl-9 lg:pl-11 bg-gray-100 dark:bg-black-2 dark:text-white placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-primary-1 text-md md:text-lg lg:text-xl"
            />
        </div>
    )
}
