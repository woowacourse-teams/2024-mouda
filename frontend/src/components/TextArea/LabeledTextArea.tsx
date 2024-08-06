import * as S from '@_components/TextArea/TextArea.style';
import { useTheme } from '@emotion/react';

import { HTMLProps } from 'react';

export interface LabeledTextAreaProps extends HTMLProps<HTMLTextAreaElement> {
  title: string;
}

export default function LabeledTextArea(props: LabeledTextAreaProps) {
  const theme = useTheme();
  const { name, title, placeholder, required, onChange, ...args } = props;

  return (
    <label htmlFor={title} css={S.labelWrapper}>
      <h3 css={S.title({ theme })}>
        {title}
        <span css={S.required({ theme })}>{required ? '*' : ''}</span>
      </h3>

      <textarea
        name={name}
        css={S.textArea({ theme })}
        placeholder={placeholder}
        id={title}
        onChange={onChange}
        {...args}
      />
    </label>
  );
}
