import { MoimInfo } from '@_types/index';
import * as S from './MoimSummary.style';
import { useTheme } from '@emotion/react';
import Tag from '@_components/Tag/Tag';

interface MoimSummaryProps {
  moimInfo: Pick<MoimInfo, 'title' | 'authorNickname' | 'status'>;
}

export default function MoimSummary(props: MoimSummaryProps) {
  const theme = useTheme();
  const {
    moimInfo: { title, authorNickname, status },
  } = props;

  return (
    <div css={S.containerStyle}>
      <div css={S.titleBox()}>
        <h1 css={S.title({ theme })}>{title}</h1>
        <Tag status={status}></Tag>
      </div>
      <div css={S.contentStyle}>
        <span css={S.contentKeyStyle()}>작성자</span>
        <span css={S.contentValueStyle}>{authorNickname}</span>
      </div>
    </div>
  );
}
