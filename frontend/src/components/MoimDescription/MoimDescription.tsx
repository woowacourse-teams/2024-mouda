import { ReactNode } from 'react';
import * as S from './MoimDescription.style';

interface MoimDescriptionProps {
  title: string;
  children: ReactNode;
}

export default function MoimDescription(props: MoimDescriptionProps) {
  const { title, children } = props;

  if (title === '') {
    return;
  }

  return (
    <div css={S.containerStyle}>
      <h2 css={S.titleStyle}>{title}</h2>
      <div css={S.descriptionStyle}>{children}</div>
    </div>
  );
}
