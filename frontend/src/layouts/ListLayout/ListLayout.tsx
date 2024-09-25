import FixedButtonWrapper from '@_layouts/components/FixedButtonWrapper/FixedButtonWrapper';
import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';
import { PropsWithChildren } from 'react';
import ListMain from './ListMain/ListMain';
import * as S from './ListLayout.style';
import { useTheme } from '@emotion/react';

function ListLayout(props: PropsWithChildren) {
  const { children } = props;

  const theme = useTheme();

  return <div css={S.listLayout({ theme })}>{children}</div>;
}

ListLayout.Header = StickyTriSectionHeader;
ListLayout.Main = ListMain;
ListLayout.PlusButtonWrapper = FixedButtonWrapper;

export default ListLayout;
