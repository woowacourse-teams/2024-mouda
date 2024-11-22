import * as S from '@_components/Input/MoimInput.style';

import { ChangeEvent, HTMLProps, useState } from 'react';

import { useTheme } from '@emotion/react';

export interface LabeledInputProps<T extends string | number>
  extends HTMLProps<HTMLInputElement> {
  title?: string;
  validateFun?: (value: T) => boolean;
}

export default function LabeledInput<T extends string | number>(
  props: LabeledInputProps<T>,
) {
  const theme = useTheme();
  const {
    name,
    title,
    type = 'text',
    placeholder,
    required,
    onChange,
    validateFun,
    ...args
  } = props;

  const [isError, setIsError] = useState(false);

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    if (onChange) {
      onChange(e);
    }

    const value = e.currentTarget.value;
    const validatedValue = type === 'number' ? Number(value) : value;

    // validateFun이 존재할 경우
    if (validateFun) {
      if (typeof validatedValue === 'string') {
        const isValid = validateFun(validatedValue as T);
        setIsError(!isValid);
      } else if (typeof validatedValue === 'number') {
        const isValid = validateFun(validatedValue as T); // number에 대한 validate 처리
        setIsError(!isValid);
      }
    }
  };

  return (
    <label htmlFor={title} css={S.labelWrapper}>
      {title && (
        <h3 css={S.title({ theme })}>
          {title}
          <span css={S.required({ theme })}>{required ? '*' : ''}</span>
        </h3>
      )}

      <input
        name={name}
        css={S.input({ theme })}
        type={type}
        placeholder={placeholder}
        id={title}
        onChange={handleInputChange}
        {...args}
      />
      <span css={S.errorMessage({ theme })}>{isError && placeholder}</span>
    </label>
  );
}
