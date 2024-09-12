import { MoimInfo } from '@_types/index';
import * as S from './MoimSummary.style';
import { useTheme } from '@emotion/react';
import Tag from '@_pages/Moim/MoimDetailPage/components/Tag/Tag';

interface MoimSummaryProps {
  moimInfo: Pick<MoimInfo, 'title' | 'status'>;
}

export default function MoimSummary(props: MoimSummaryProps) {
  const theme = useTheme();
  const {
    moimInfo: { title, status },
  } = props;

  return (
    <div css={S.containerStyle}>
      <div css={S.titleBox()}>
        <h1 css={S.title({ theme })}>{title}</h1>
        <Tag status={status}></Tag>
      </div>
    </div>
  );
}
