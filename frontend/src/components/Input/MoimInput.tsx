import * as S from '@_components/Input/MoimInput.style';

import { HTMLProps } from 'react';

export interface LabeledInputProps extends HTMLProps<HTMLInputElement> {
  title: string;
}

export default function LabeledInput(props: LabeledInputProps) {
  const { name, title, type, placeholder, required, onChange, ...args } = props;

  return (
    <label htmlFor={title}>
      <h3 css={S.title}>
        {title}
        <span css={S.required}>{required ? '*' : ''}</span>
      </h3>

      <input
        name={name}
        css={S.input}
        type={type}
        placeholder={placeholder}
        id={title}
        onChange={onChange}
        {...args}
      />
    </label>
  );
}
