import * as S from './TriSectionHeader.style';

import { Fragment, ReactNode } from 'react';

interface TriSectionHeaderProps {
  left?: ReactNode;
  center?: ReactNode;
  right?: ReactNode;

  borderBottomColor?: string;
}

export default function TriSectionHeader(props: TriSectionHeaderProps) {
  const { left, center, right, borderBottomColor } = props;
  return (
    <Fragment>
      <header css={S.getTriSectionHeaderStyle({ borderBottomColor })}>
        <div css={S.leftSectionStyle}>{left}</div>
        <div css={S.middleSectionStyle}>{center}</div>
        <div css={S.rightSectionStyle}>{right}</div>
      </header>
    </Fragment>
  );
}
