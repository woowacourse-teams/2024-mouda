import * as S from '@_components/Input/MoimInput.style';
import { useTheme } from '@emotion/react';

import { HTMLProps } from 'react';

export interface LabeledInputProps extends HTMLProps<HTMLInputElement> {
  title: string;
}

export default function LabeledInput(props: LabeledInputProps) {
  const theme = useTheme();
  const {
    name,
    title,
    type = 'text',
    placeholder,
    required,
    onChange,
    ...args
  } = props;

  return (
    <label htmlFor={title} css={S.labelWrapper}>
      <h3 css={S.title({ theme })}>
        {title}
        <span css={S.required({ theme })}>{required ? '*' : ''}</span>
      </h3>

      <input
        name={name}
        css={S.input({ theme })}
        type={type}
        placeholder={placeholder}
        id={title}
        onChange={onChange}
        {...args}
      />
    </label>
  );
}
